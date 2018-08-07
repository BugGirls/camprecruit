package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzSafetyNews;

import core.dao.Dao;


   /**
    * TzzSafetyNews数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzSafetyNewsDao extends  Dao<TzzSafetyNews>{
	
	List<TzzSafetyNews> getSafetyNewsListByIds(List<Integer>ids);
	List<TzzSafetyNews> queryHotByorder(String hql );
	List<TzzSafetyNews> queryAllByorder(String hql ,int page);
	int pageAllByorder(String hql );
	List<TzzSafetyNews> searchSafetyNews(String hql , int family, int page); 
	int searchSafetyNewsPage(String hql , int family ); 
	
	List<TzzSafetyNews> searchByName( String hql,String name,int page);
	int searchByNameNum( String hql,String name );
	List<TzzSafetyNews> getindexList(String hql, int page);
	Integer getindexListCount(String hql); 
}
