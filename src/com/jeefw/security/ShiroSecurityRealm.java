package com.jeefw.security;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.jeefw.dao.sys.SysUserDao;
import com.jeefw.model.sys.Role;
import com.jeefw.model.sys.SysUser;

@Component
public class ShiroSecurityRealm extends AuthorizingRealm {

	@Resource
	private SysUserDao sysUserDao;

	public ShiroSecurityRealm() {
		setName("ShiroSecurityRealm"); // This name must match the name in the SysUser class's getPrincipals() method
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser user = sysUserDao.getByProerties("email", token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long userId = (Long) principals.fromRealm(getName()).iterator().next();
		SysUser user = sysUserDao.get(userId);
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Role role : user.getRoles()) {
				info.addRole(role.getRoleKey());
				info.addStringPermissions(role.getPermissions());
			}
			return info;
		} else {
			return null;
		}
	}

}
