package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CompanyShenheDao;
import com.jeefw.model.sys.CompanyShenhe;

import core.dao.BaseDao;


   /**
    * CompanyShenhe的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class CompanyShenheDaoImpl extends  BaseDao<CompanyShenhe> implements CompanyShenheDao {

	public CompanyShenheDaoImpl() {
		super(CompanyShenhe.class);
	}

	@Override
	public List<CompanyShenhe> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from CompanyShenhe where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

