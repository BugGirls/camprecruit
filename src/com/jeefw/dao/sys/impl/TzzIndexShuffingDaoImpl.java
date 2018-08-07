package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzIndexShuffingDao;
import com.jeefw.model.sys.TzzIndexShuffing;

import core.dao.BaseDao;


   /**
    * TzzIndexShuffing的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzIndexShuffingDaoImpl extends  BaseDao<TzzIndexShuffing> implements TzzIndexShuffingDao {

	public TzzIndexShuffingDaoImpl() {
		super(TzzIndexShuffing.class);
	}

}

