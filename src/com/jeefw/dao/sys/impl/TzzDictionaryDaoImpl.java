package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.TzzDictionary;

import core.dao.BaseDao;


   /**
    * TzzDictionary的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzDictionaryDaoImpl extends  BaseDao<TzzDictionary> implements TzzDictionaryDao {

	public TzzDictionaryDaoImpl() {
		super(TzzDictionary.class);
	}

	@Override
	public void deleteTzzDicById(int tdId) {
		Query query = this.getSession().createSQLQuery("delete from tzz_dictionary where id = :tdId");
		query.setParameter("tdId", tdId);
		query.executeUpdate(); 
	}

	@Override
	public List<TzzDictionary> getDictionaryByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from TzzDictionary where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

}

