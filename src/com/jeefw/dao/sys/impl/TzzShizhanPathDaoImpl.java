package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzShizhanPathDao;
import com.jeefw.model.sys.TzzShizhanPath;

import core.dao.BaseDao;


   /**
    * TzzShizhanPath的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzShizhanPathDaoImpl extends  BaseDao<TzzShizhanPath> implements TzzShizhanPathDao {

	public TzzShizhanPathDaoImpl() {
		super(TzzShizhanPath.class);
	}

}

