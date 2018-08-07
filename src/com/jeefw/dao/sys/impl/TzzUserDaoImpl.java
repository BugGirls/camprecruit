package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserDao;
import com.jeefw.model.sys.TzzUser;

import core.dao.BaseDao;


   /**
    * TzzUser的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserDaoImpl extends  BaseDao<TzzUser> implements TzzUserDao {

	public TzzUserDaoImpl() {
		super(TzzUser.class);
	}

	@Override
	public List<TzzUser> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from TzzUser where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

