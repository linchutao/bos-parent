package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleDao roleDao;
	
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		if(StringUtils.isNotBlank(functionIds)) {
			String[] fIds = functionIds.split(",");
			for (String fId : fIds) {
				Function function = new Function(fId);
				model.getFunctions().add(function);
			}
		}
	}

	public void  pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
