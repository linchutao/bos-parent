package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface IRegionService {

	void savaOrUpdate(List<Region> regions);

	void pageQuery(PageBean pageBean);

	List<Region> findListByQ(String q);

	List<Region> findAll();
	
}
