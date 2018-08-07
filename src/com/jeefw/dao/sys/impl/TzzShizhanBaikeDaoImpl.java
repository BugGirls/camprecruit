package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzShizhanBaikeDao;
import com.jeefw.model.sys.TzzShizhanBaike;

import core.dao.BaseDao;


   /**
    * TzzShizhanBaike的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzShizhanBaikeDaoImpl extends  BaseDao<TzzShizhanBaike> implements TzzShizhanBaikeDao {

	public TzzShizhanBaikeDaoImpl() {
		super(TzzShizhanBaike.class);
	}

}

