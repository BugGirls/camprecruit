package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzHotlinkDao;
import com.jeefw.model.sys.TzzHotlink;

import core.dao.BaseDao;


   /**
    * TzzHotlink的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzHotlinkDaoImpl extends  BaseDao<TzzHotlink> implements TzzHotlinkDao {

	public TzzHotlinkDaoImpl() {
		super(TzzHotlink.class);
	}

}

