package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserLogDao;
import com.jeefw.model.sys.TzzUserLog;

import core.dao.BaseDao;


   /**
    * TzzUserLog的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserLogDaoImpl extends  BaseDao<TzzUserLog> implements TzzUserLogDao {

	public TzzUserLogDaoImpl() {
		super(TzzUserLog.class);
	}

}

