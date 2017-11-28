package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;
	private String decidedId;
	/**
	 * 添加分区方法
	 * 
	 * @return
	 */
	public String add() {
		subareaService.add(model);
		return LIST;
	}

	/**
	 * 分区分页查询方法
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		if (StringUtils.isNotBlank(addresskey)) {
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				dc.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				dc.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				dc.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		java2json(pageBean, new String[] { "currentPage", "pageSize", "detachedCriteria", "decidedzone", "subareas" });
		return NONE;
	}
	/**
	 * 文件导出方法
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		List<Subarea> list = subareaService.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("分区管理");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		for (Subarea subarea : list) {
			HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
			row.createCell(0).setCellValue(subarea.getId());
			row.createCell(1).setCellValue(subarea.getStartnum());
			row.createCell(2).setCellValue(subarea.getEndnum());
			row.createCell(3).setCellValue(subarea.getPosition());
			row.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		String fileName = "分区管理.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		fileName = FileUtils.encodeDownloadFilename(fileName, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+fileName);
		workbook.write(outputStream);
		return NONE;
	}
	/**
	 * 加载关联分区的方法
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findByNotAssociation();
		this.java2json(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	/**
	 * 定区关联分区的方法
	 */
	public String findListByDecidedzoneId() {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedId);
		this.java2json(list, new String[]{"subareas","decidedzone"});
		return NONE;
	}

	public void setDecidedId(String decidedId) {
		this.decidedId = decidedId;
	}
	/**
	 * 查找分区数量按照分区排列
	 */
	public String findSubareasGroupProvince() {
		List<Object> list = subareaService.findSubareasGroupProvince();
		this.java2json(list, new String[]{});
		return NONE;
	}
}
