package com.jeefw.dao.sys.impl;


import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.EmailphoneDao;
import com.jeefw.model.sys.Emailphone;

import core.dao.BaseDao;


   /**
    * Emailphone的数据持久层的实现类 
    * 2015/10/08 10:48:31  tudou
    */ 
@Repository
public class EmailphoneDaoImpl extends  BaseDao<Emailphone> implements EmailphoneDao {

	public EmailphoneDaoImpl() {
		super(Emailphone.class);
	}

}

