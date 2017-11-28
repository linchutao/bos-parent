package com.itheima.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.opensymphony.xwork2.ActionContext;

public class BOSUtils {
	//获取session
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	//获取loginUser
	public static User getLoginUser() {
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
