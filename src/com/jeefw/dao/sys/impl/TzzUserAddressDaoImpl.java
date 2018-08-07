package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserAddressDao;
import com.jeefw.model.sys.TzzUserAddress;

import core.dao.BaseDao;


   /**
    * TzzUserAddress的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserAddressDaoImpl extends  BaseDao<TzzUserAddress> implements TzzUserAddressDao {

	public TzzUserAddressDaoImpl() {
		super(TzzUserAddress.class);
	}

}

