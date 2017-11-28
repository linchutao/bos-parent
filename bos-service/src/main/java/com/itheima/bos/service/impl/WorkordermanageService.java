package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordrmanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
@Service
@Transactional
public class WorkordermanageService implements IWorkordermanageService{
	@Autowired
	private IWorkordrmanageDao workordermanageDao;
	
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

}
