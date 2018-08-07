package com.jeefw.service.sys;

import com.jeefw.model.sys.Role;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface RoleService extends Service<Role> {

	void deleteSysUserAndRoleByRoleId(Long roleId);

}
