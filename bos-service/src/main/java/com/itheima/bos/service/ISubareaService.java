package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {

	void add(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findByNotAssociation();

	List<Subarea> findListByDecidedzoneId(String decidedId);

	List<Object> findSubareasGroupProvince();
	
}
