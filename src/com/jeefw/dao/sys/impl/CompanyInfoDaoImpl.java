package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CompanyInfoDao;
import com.jeefw.model.sys.CompanyInfo;

import core.dao.BaseDao;


   /**
    * CompanyInfo的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class CompanyInfoDaoImpl extends  BaseDao<CompanyInfo> implements CompanyInfoDao {

	public CompanyInfoDaoImpl() {
		super(CompanyInfo.class);
	}

	@Override
	public List<CompanyInfo> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from CompanyInfo where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

