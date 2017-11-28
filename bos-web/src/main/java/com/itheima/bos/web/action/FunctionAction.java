package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private IFunctionService functionService;
	/**
	 * 加载父功能点的方法
	 */
	public String listajax() {
		List<Function> list = functionService.findAll();
		this.java2json(list, new String[]{"parentFunction","roles","code","description"
				,"page","generatemenu","zindex"});
		return NONE;
	}
	/**
	 * 添加权限
	 */
	public String add() {
		functionService.save(model);
		return LIST;
	}
	/**
	 * 分页查询
	 */
	public String pageQuery() {
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		this.java2json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	 * 加载菜单
	 */
	public String findMenu() {
		List<Function> list =  functionService.findMenu();
		this.java2json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
}
