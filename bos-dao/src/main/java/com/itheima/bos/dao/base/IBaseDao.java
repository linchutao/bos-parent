package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public List<T> findAll();
	List<T> findByCriteria(DetachedCriteria criteria);
	public T findById(Serializable id);
	public void executeUpdate(String queryName,Object... objects);
	public void pageQuery(PageBean pageBean);
}
