package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;

	public User login(User model) {
		//使用md5加密
		String password = MD5Utils.md5(model.getPassword());
		return userDao.findUserByUsernameAndPassword(model.getUsername(),password);
	}

	//修改密码
	public void editPassword(String password, String id) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword",password,id);
	}

	//添加用户并关联角色
	public void save(User model, String[] roleIds) {
		String password = MD5Utils.md5(model.getPassword());
		model.setPassword(password);
		userDao.save(model);
		//关联角色
		if(roleIds!=null&&roleIds.length>0) {
			for (String roleId : roleIds) {
				Role role = new Role(roleId);
				model.getRoles().add(role);
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
