package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionService implements IRegionService {

	@Autowired
	private IRegionDao regionDao;
	
	public void savaOrUpdate(List<Region> regions) {
		for (Region region : regions) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}


	public List<Region> findListByQ(String q) {
		return regionDao.findListByQ(q);
	}

	
	public List<Region> findAll() {
		return regionDao.findAll();
	}

}
