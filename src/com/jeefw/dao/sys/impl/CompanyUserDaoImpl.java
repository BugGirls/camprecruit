package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CompanyUserDao;
import com.jeefw.model.sys.CompanyUser;

import core.dao.BaseDao;


   /**
    * CompanyUser的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class CompanyUserDaoImpl extends  BaseDao<CompanyUser> implements CompanyUserDao {

	public CompanyUserDaoImpl() {
		super(CompanyUser.class);
	}

	@Override
	public List<CompanyUser> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from CompanyUser where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

