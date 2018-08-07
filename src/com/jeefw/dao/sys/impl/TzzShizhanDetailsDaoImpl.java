package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzShizhanDetailsDao;
import com.jeefw.model.sys.TzzShizhanDetails;

import core.dao.BaseDao;


   /**
    * TzzShizhanDetails的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzShizhanDetailsDaoImpl extends  BaseDao<TzzShizhanDetails> implements TzzShizhanDetailsDao {

	public TzzShizhanDetailsDaoImpl() {
		super(TzzShizhanDetails.class);
	}

}

