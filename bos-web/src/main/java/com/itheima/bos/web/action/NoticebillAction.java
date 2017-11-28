package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	@Autowired
	private ICustomerService proxy;
	@Autowired
	private INoticebillService noticebillService;
	/**
	 * 通过电话号码查询客户信息
	 * @return
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = proxy.findCustomerByTelephone(telephone);
		this.java2json(customer, new String[]{});
		return NONE;
	}
	/**
	 * 添加业务通知单并且实现自动分单
	 */
	public String add() {
		noticebillService.save(model);
		return "noticebill_add";
	}
}
