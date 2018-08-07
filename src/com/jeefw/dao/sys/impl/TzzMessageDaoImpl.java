package com.jeefw.dao.sys.impl;


import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzMessageDao;
import com.jeefw.model.sys.TzzMessage;

import core.dao.BaseDao;


   /**
    * TzzMessage的数据持久层的实现类 
    * 2015/12/07 10:19:43  tudou
    */ 
@Repository
public class TzzMessageDaoImpl extends  BaseDao<TzzMessage> implements TzzMessageDao {

	public TzzMessageDaoImpl() {
		super(TzzMessage.class);
	}

}

