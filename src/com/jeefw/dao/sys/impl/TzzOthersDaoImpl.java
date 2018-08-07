package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzOthersDao;
import com.jeefw.model.sys.TzzOthers;

import core.dao.BaseDao;


   /**
    * TzzOthers的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzOthersDaoImpl extends  BaseDao<TzzOthers> implements TzzOthersDao {

	public TzzOthersDaoImpl() {
		super(TzzOthers.class);
	}

}

