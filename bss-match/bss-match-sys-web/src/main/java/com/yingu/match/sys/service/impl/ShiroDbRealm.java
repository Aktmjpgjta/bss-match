package com.yingu.match.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.yingu.match.common.model.Parameter;
import com.yingu.match.sys.SysConst;
import com.yingu.match.sys.model.Resources;
import com.yingu.match.sys.model.User;
import com.yingu.match.sys.service.ISysService;
public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private ISysService sysService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = new User();
		user.setUsername(token.getUsername());
		user = (User)sysService.execute(new Parameter("userService","findTByTOne",user)).getResult(); //userService.findTByTOne(user);
		if(user == null) {  
            throw new UnknownAccountException();//没找到帐号  
        } 
		if(SysConst.STATUS_DELETE.equals(user.getStatus())) {  
            throw new LockedAccountException(); //帐号锁定  
        }
		// 认证缓存信息
		return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
	}

	/**
	 * 
	 * Shiro权限认证
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User shiroUser = (User) principals.getPrimaryPrincipal();
		Resources resources = new Resources();
		resources.setId(shiroUser.getId());
	//	resources.setType("2");
		List<Resources> roleList = (List<Resources>)sysService.execute(new Parameter("resourcesService","findTByT",resources)).getResult(); //resourcesService.findTByT(resources);
		Set<String> urlSet = new HashSet<String>();
		for (Resources roleId : roleList) {
			urlSet.add(roleId.getUrl());
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(urlSet);
		return info;
	}

}
