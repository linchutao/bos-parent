package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	List<Function> findFunctionByUserId(String id);

	List<Function> findAllMenu();

	List<Function> findMenuByUserId(String id);
	
}
