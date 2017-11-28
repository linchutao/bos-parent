package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	private File regionFile;// 导入文件属性注入
	private String q;
	@Autowired
	private IRegionService regionService;
	/**
	 * 导入文件方法
	 * @param regionFile
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws Exception {
		if(regionFile!=null) {
			//获取workbook对象
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
			//获取sheet1
			HSSFSheet sheet = workbook.getSheet("sheet1");
			List<Region> regions = new ArrayList<Region>();
			//遍历sheet1
			for (Row row : sheet) {
				if(row.getRowNum()==0) {//如果是第一行,数据跳过
					continue;
				}
				String id = row.getCell(0).getStringCellValue();      
				String province = row.getCell(1).getStringCellValue(); 
				String city = row.getCell(2).getStringCellValue();     
				String district = row.getCell(3).getStringCellValue(); 
				String postcode = row.getCell(4).getStringCellValue();
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				//删去省市区汉字
				province = province.substring(0, province.length()-1);
				city = city.substring(0, city.length()-1);
				district = district.substring(0, district.length()-1);
				
				String info = province + city + district;
				
				String[] strings = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(strings);
				String citycode =	PinYin4jUtils.hanziToPinyin(city,"");
				region.setShortcode(shortcode);
				region.setCitycode(citycode);
				regions.add(region);
			}
			regionService.savaOrUpdate(regions);
		}
		return NONE;
	}
	
	//分页查询方法
	public String pageQuery() throws IOException{
		regionService.pageQuery(pageBean);
		java2json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	/**
	 * 查询区域方法
	 * @param regionFile
	 */
	public String listajax() {
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findListByQ(q);
		}else {
			list = regionService.findAll();
		}
		this.java2json(list, new String[]{"subareas"});
		return NONE;
	}
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
	
}
