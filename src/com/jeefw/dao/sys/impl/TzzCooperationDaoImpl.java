package com.jeefw.dao.sys.impl;


 
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzCooperationDao;
import com.jeefw.model.sys.TzzCooperation; 

import core.dao.BaseDao;


   /**
    * TzzCooperation的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzCooperationDaoImpl extends  BaseDao<TzzCooperation> implements TzzCooperationDao {

	public TzzCooperationDaoImpl() {
		super(TzzCooperation.class);
	}
	
} 