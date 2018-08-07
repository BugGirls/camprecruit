package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.RoleDao;
import com.jeefw.model.sys.Role;
import com.jeefw.service.sys.RoleService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

	private RoleDao roleDao;

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
		this.dao = roleDao;
	}

	@Override
	public void deleteSysUserAndRoleByRoleId(Long roleId) {
		roleDao.deleteSysUserAndRoleByRoleId(roleId);
		
		
	}

}
