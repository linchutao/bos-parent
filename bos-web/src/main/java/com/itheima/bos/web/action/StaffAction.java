package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	@Autowired
	private IStaffService staffService;
	
	//保存取派员
	public String add() {
		staffService.save(model);
		return LIST;
	}
	private int page;
	private int rows;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	//分页查询方法
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		java2json(pageBean,new String[]{"currentPage","pageSize","detachedCriteria","decidedzones"});
		return NONE;
	}
	/**
	 * 删除取派员方法
	 */
	private String ids;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 批量删除方法
	 * @return
	 */
	@RequiresPermissions("staff-delete")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 修改取派员信息方法
	 */
	public String edit() {
		staffService.update(model);
		return LIST;
	}
	/**
	 * 加载取派员信息方法
	 */
	public String listajax() {
		List<Staff> list =  staffService.findListNotDelete();
		java2json(list, new String[]{"decidedzones"});
		return NONE;
	}
}
