package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	//属性驱动，接收页面输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Autowired
	private ICustomerService proxy;
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户登录
	 */
	public String login(){
		List<Customer> list2 = proxy.findAll();
		//从Session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验验证码是否输入正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)){
			//获取当前用户对象
			Subject subject = SecurityUtils.getSubject();
			//用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
				User user = (User) subject.getPrincipal();
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}
			return HOME;
		}else{
			//输入的验证码错误,设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}
	/**
	 * 用户登录
	 */
	public String login_bak(){
		List<Customer> list2 = proxy.findAll();
		System.out.println(list2);
		//从Session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验验证码是否输入正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)){
			//输入的验证码正确
			User user = userService.login(model);
			if(user != null){
				//登录成功,将user对象放入session，跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			}else{
				//登录失败，,设置提示信息，跳转到登录页面
				//输入的验证码错误,设置提示信息，跳转到登录页面
				this.addActionError("用户名或者密码输入错误！");
				return LOGIN;
			}
		}else{
			//输入的验证码错误,设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}
	/**
	 * 用户注销
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	
	/**
	 *修改密码 
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		String f = "1";
		try {
			userService.editPassword(model.getPassword(),BOSUtils.getLoginUser().getId());
		}catch(Exception e) {
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
	/**
	 * 属性驱动
	 */
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	/**
	 * 添加用户
	 */
	public String add() {
		userService.save(model,roleIds);
		return LIST;
	}
	/**
	 * 用户分页查询
	 */
	public String pageQuery() {
		userService.pageQuery(pageBean);
		this.java2json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}
}
