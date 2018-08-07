package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query; 
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserSetvipDao; 
import com.jeefw.model.sys.TzzUserSetvip;

import core.dao.BaseDao;


   /**
    * TzzUserSetvip的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserSetvipDaoImpl extends  BaseDao<TzzUserSetvip> implements TzzUserSetvipDao {

	public TzzUserSetvipDaoImpl() {
		super(TzzUserSetvip.class);
	}
	//  
	public List<TzzUserSetvip> querySensorList() {
		String sql ="from TzzUserSetvip order by minAmount desc"; 
		Query query =  this.getSession().createQuery(sql); 
		return query.list();
	}
 
}

