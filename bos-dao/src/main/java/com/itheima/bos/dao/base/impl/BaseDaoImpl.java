package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	private Class<T> entityClass;
	
	public BaseDaoImpl() {
		ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = superClass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	
	@Resource//根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}
	
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public List<T> findAll() {
		String hql = "From "+entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}
	
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	//执行更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		query.executeUpdate();
	}

	/**
	 * 通用分页查询方法
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//查询总记录数
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = countList.get(0);
		pageBean.setTotal(total.intValue());
		//分页查询
		detachedCriteria.setProjection(null);
		//指定hibernate封装框架的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCriteria(DetachedCriteria criteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
	}
	
}
