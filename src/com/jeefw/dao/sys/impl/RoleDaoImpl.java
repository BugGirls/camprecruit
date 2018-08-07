package com.jeefw.dao.sys.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.RoleDao;
import com.jeefw.model.sys.Role;

import core.dao.BaseDao;

/**
 * 角色的数据持久层的实现类
 * @ 
 */
@Repository
public class RoleDaoImpl extends BaseDao<Role> implements RoleDao {

	public RoleDaoImpl() {
		super(Role.class);
	}

	@Override
	public void deleteSysUserAndRoleByRoleId(Long roleId) {
		Query query = this.getSession().createSQLQuery("delete from sysuser_role where role_id = :roleId");
		query.setParameter("roleId", roleId);
		query.executeUpdate();
	}

}
