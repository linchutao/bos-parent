package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	@Autowired
	private IDecidedzoneService decidedzoneService;
	@Autowired
	private ICustomerService proxy;
	private String[] subareaid;
	private List<Integer> customerIds;//属性驱动
	/**
	 * 添加定区方法
	 * @return
	 */
	public String add() {
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	/**
	 * 定区查询分页方法
	 * @param subareaid
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		java2json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","subareas","decidedzones"});
		return NONE;
	}
	/**
	 * 查询未关联定区的客户的方法
	 */
	public String findListNotAssociation(){
		List<Customer> list2 = proxy.findListNotAssociation();
		java2json(list2, new String[]{});
		return NONE;
	}
	/**
	 * 查询已关联定区的客户
	 */
	public String findListHasAssociation(){
		String id = model.getId();
		List<Customer> list3 = proxy.findListHasAssociation(id);
		java2json(list3, new String[]{});
		return NONE;
	}
	/**
	 *定区关联客户 
	 */
	public String assigncustomerstodecidedzone() {
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	
}
