package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;
@Repository
public class RegionDao extends BaseDaoImpl<Region> implements IRegionDao {

	public List<Region> findListByQ(String q) {
		String hql = "FROM Region r where r.shortcode like ? Or r.citycode like ?"
					+" OR r.province like ? or r.city like ? or r.district like ?";
		return (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
	}
	
}
