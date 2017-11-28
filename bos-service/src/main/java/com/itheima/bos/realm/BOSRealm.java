package com.itheima.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			//如果是系统管理员,查询所有权限
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			list = functionDao.findByCriteria(detachedCriteria);
		}else {
			//如果不是系统管理员,查询相应的权限
			list = functionDao.findFunctionByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String username = usernamePasswordToken.getUsername();
		//根据用户名查询用户密码
		User user = userDao.findUserByUsername(username);
		if(user==null) {
			//用户名不存在
			return null;
		}
		////如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		return info;
	}

}
