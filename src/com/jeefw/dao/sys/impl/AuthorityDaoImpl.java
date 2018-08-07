package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.AuthorityDao;
import com.jeefw.model.sys.Authority;

import core.dao.BaseDao;

/**
 * 菜单的数据持久层的实现类
 * @ 
 */
@Repository
public class AuthorityDaoImpl extends BaseDao<Authority> implements AuthorityDao {

	public AuthorityDaoImpl() {
		super(Authority.class);
	}

}
