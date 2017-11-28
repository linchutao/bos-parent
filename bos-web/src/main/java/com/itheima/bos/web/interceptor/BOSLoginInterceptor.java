package com.itheima.bos.web.interceptor;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor extends MethodFilterInterceptor{

	//自定义拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = BOSUtils.getLoginUser();
		if(user==null) {
			return "login";
		}else {
			return invocation.invoke();
		}
	}

}
