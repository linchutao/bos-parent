package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.PageBean;

public interface IFunctionService {

	List<Function> findAll();

	void save(Function model);

	void pageQuery(PageBean pageBean);

	List<Function> findMenu();
	
}
