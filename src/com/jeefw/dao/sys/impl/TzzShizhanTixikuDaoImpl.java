package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzShizhanTixikuDao;
import com.jeefw.model.sys.TzzShizhanTixiku;

import core.dao.BaseDao;


   /**
    * TzzShizhanTixiku的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzShizhanTixikuDaoImpl extends  BaseDao<TzzShizhanTixiku> implements TzzShizhanTixikuDao {

	public TzzShizhanTixikuDaoImpl() {
		super(TzzShizhanTixiku.class);
	}

}

