package com.jeefw.dao.sys.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzSafetyNewsDao;
import com.jeefw.model.sys.TzzSafetyNews;

import core.dao.BaseDao;


   /**
    * TzzSafetyNews的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzSafetyNewsDaoImpl extends  BaseDao<TzzSafetyNews> implements TzzSafetyNewsDao {

	public TzzSafetyNewsDaoImpl() {
		super(TzzSafetyNews.class);
	}

	@Override
	public List<TzzSafetyNews> getSafetyNewsListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from TzzSafetyNews where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public List<TzzSafetyNews> queryHotByorder(String hql ) {
		Query query = this.getSession().createQuery(hql); 
		query.setMaxResults(6);
		return query.list(); 
	}


	@Override
	public int pageAllByorder(String hql ) {
		Query query = this.getSession().createQuery(hql);   
		return query.list().size(); 
	}
	@Override
	public List<TzzSafetyNews> queryAllByorder(String hql,int page) {
		Query query = this.getSession().createQuery(hql); 
		query.setFirstResult(page);
		query.setMaxResults(8); 
		return query.list(); 
	}

	@Override
	public List<TzzSafetyNews> searchSafetyNews(String hql, int family, int page) {
		Query query = this.getSession().createQuery(hql); 
		if(family!=0){
			query.setParameter(0 , family);
		}
		query.setFirstResult(page);
		query.setMaxResults(9); 
		return query.list();  
	}

	@Override
	public int searchSafetyNewsPage(String hql, int family) {
		Query query = this.getSession().createQuery(hql); 
		if(family!=0){
			query.setParameter(0 , family);
		}
		return query.list().size();   
	}

	@Override
	public List<TzzSafetyNews> searchByName(String hql, String name, int page) {
		Query query = this.getSession().createQuery(hql); 
		if(!name.equals("")){
			query.setParameter(0 , "%"+name+"%");
			query.setParameter(1 , "%"+name+"%");
			query.setParameter(2 , "%"+name+"%");
		}
		query.setFirstResult(page);
		query.setMaxResults(9); 
		return query.list();  
	}

	@Override
	public int searchByNameNum(String hql, String name) {
		Query query = this.getSession().createQuery(hql); 
		if(!name.equals("")){
			query.setParameter(0 , "%"+name+"%");
			query.setParameter(1 , "%"+name+"%");
			query.setParameter(2 , "%"+name+"%");
		}
		return query.list().size();   
	}

	@Override
	public List<TzzSafetyNews> getindexList(String hql, int page) {
		Query query =  this.getSession().createQuery(hql).setFirstResult(page).setMaxResults(9);
		return query.list(); 
	}

	@Override
	public Integer getindexListCount(String hql) {
		Query query = this.getSession().createQuery(hql); 
		return Integer.parseInt(query.uniqueResult().toString());
	}

}

