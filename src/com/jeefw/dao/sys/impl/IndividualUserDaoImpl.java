package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.IndividualUserDao;
import com.jeefw.model.sys.IndividualUser;

import core.dao.BaseDao;

/**
 * 个人用户的数据持久层的实现类
 * @ 
 */
@Repository
public class IndividualUserDaoImpl extends BaseDao<IndividualUser> implements IndividualUserDao {

	public IndividualUserDaoImpl() {
		super(IndividualUser.class);
	}

}
