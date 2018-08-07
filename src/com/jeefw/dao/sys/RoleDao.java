package com.jeefw.dao.sys;

import com.jeefw.model.sys.Role;

import core.dao.Dao;

/**
 * 角色的数据持久层的接口
 * @ 
 */
public interface RoleDao extends Dao<Role> {

	void deleteSysUserAndRoleByRoleId(Long roleId);

}
