package com.jeefw.dao.sys.impl;


import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzSmsDao;
import com.jeefw.model.sys.TzzSms;

import core.dao.BaseDao;


   /**
    * TzzSms的数据持久层的实现类 
    * 2015/10/12 10:30:22  tudou
    */ 
@Repository
public class TzzSmsDaoImpl extends  BaseDao<TzzSms> implements TzzSmsDao {

	public TzzSmsDaoImpl() {
		super(TzzSms.class);
	}

}

