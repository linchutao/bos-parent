package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.INoticebillDao;
import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.ICustomerService;
@Service
@Transactional
public class NoticebillService implements INoticebillService {
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private ICustomerService proxy;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workDao;
	public void save(Noticebill model) {
		User user = BOSUtils.getLoginUser();
		model.setUser(user);
		//添加业务通知单
		noticebillDao.save(model);
		//自动分单
		//获取定区id
		String decidedzoneId = proxy.findDecidedzoneIdByAddress(model.getPickaddress());
		if(decidedzoneId!=null) {
			//根据定区id获取定区
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			//获取定区取派员
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务通知单关联取派员对象
			//设置分单类型为：自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//创建一个新的工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workbill.setNoticebill(model);
			workbill.setPickstate(Workbill.PICKSTATE_NO);
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			workbill.setType(Workbill.TYPE_1);
			workDao.save(workbill);
		}else {
			//设置分单类型为：人工分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		
	}

}
