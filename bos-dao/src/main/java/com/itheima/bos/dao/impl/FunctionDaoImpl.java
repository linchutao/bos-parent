package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	public List<Function> findAll() {
		String hql = "FROM Function f where f.parentFunction is null";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	public List<Function> findFunctionByUserId(String userId) {
		String hql = "SELECT DISTINCT f From Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u where u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, userId);
	}

	public List<Function> findAllMenu() {
		String hql = "from Function f where f.generatemenu = '1' order by f.zindex desc";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	public List<Function> findMenuByUserId(String id) {
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id = ? and f.generatemenu = '1' order by f.zindex desc";
		return (List<Function>) this.getHibernateTemplate().find(hql, id);
	}
	
}
