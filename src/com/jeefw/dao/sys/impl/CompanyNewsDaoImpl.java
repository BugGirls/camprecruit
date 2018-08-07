package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CompanyNewsDao;
import com.jeefw.model.sys.CompanyNews;

import core.dao.BaseDao;


   /**
    * CompanyNews的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class CompanyNewsDaoImpl extends  BaseDao<CompanyNews> implements CompanyNewsDao {

	public CompanyNewsDaoImpl() {
		super(CompanyNews.class);
	}

	@Override
	public List<CompanyNews> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from CompanyNews where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

