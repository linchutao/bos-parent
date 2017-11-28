package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	@Autowired
	private IRoleService roleService;
	
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/**
	 * 天价一个角色,并关联权限
	 * @return
	 */
	public String add() {
		roleService.save(model,functionIds);
		return LIST;
	}
	/**
	 * 角色分页查询
	 */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.java2json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	/**
	 * 加载角色的ajax请求
	 */
	public String listajax() {
		List<Role> list = roleService.findAll();
		this.java2json(list, new String[]{"functions","users"});
		return NONE;
	}
}
