package com.jeefw.dao.sys.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CooperationCollegesDao;
import com.jeefw.model.sys.CooperationColleges;

import core.dao.BaseDao;


   /**
    * CooperationColleges的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class CooperationCollegesDaoImpl extends  BaseDao<CooperationColleges> implements CooperationCollegesDao {

	public CooperationCollegesDaoImpl() {
		super(CooperationColleges.class);
	}

	@Override
	public List<CooperationColleges> getCooperationCollegesListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		Query query = this.getSession().createQuery("from CooperationColleges where id in (:ids)");
		query = query.setParameterList("ids", ids);
		return query.list();
	}

	@Override
	public List<CooperationColleges> queryHotByorder(String hql ) {
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
	public List<CooperationColleges> queryAllByorder(String hql,int page) {
		Query query = this.getSession().createQuery(hql); 
		query.setFirstResult(page);
		query.setMaxResults(8); 
		return query.list(); 
	}

	@Override
	public List<CooperationColleges> searchCooperationColleges(String hql, int family, int page) {
		Query query = this.getSession().createQuery(hql); 
		if(family!=0){
			query.setParameter(0 , family);
		}
		query.setFirstResult(page);
		query.setMaxResults(9); 
		return query.list();  
	}

	@Override
	public int searchCooperationCollegesPage(String hql, int family) {
		Query query = this.getSession().createQuery(hql); 
		if(family!=0){
			query.setParameter(0 , family);
		}
		return query.list().size();   
	}

	@Override
	public List<CooperationColleges> searchByName(String hql, String name, int page) {
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
	public List<CooperationColleges> getindexList(String hql, int page) {
		Query query =  this.getSession().createQuery(hql).setFirstResult(page).setMaxResults(9);
		return query.list(); 
	}

	@Override
	public Integer getindexListCount(String hql) {
		Query query = this.getSession().createQuery(hql); 
		return Integer.parseInt(query.uniqueResult().toString());
	}
	
	@Override
	public Map<Integer,String> getCooperationCollegesMap() {
		String sql = "select id,name from alliance_business";
		Query query = this.getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		Map<Integer,String> result = new HashMap<Integer,String>();
		for (Object[] obj : list) {
			result.put((Integer)obj[0],(String)obj[1]);
		}
		return result;
	}

}

