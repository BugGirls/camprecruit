package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzActivityArea;

import core.dao.Dao;


   /**
    * TzzActivityArea数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzActivityAreaDao extends  Dao<TzzActivityArea>{
	
	List<TzzActivityArea> getActivityAreaListByIds(List<Integer>ids);
	List<TzzActivityArea> queryHotByorder(String hql );
	List<TzzActivityArea> queryAllByorder(String hql ,int page);
	int pageAllByorder(String hql );
	List<TzzActivityArea> searchActivityArea(String hql , int family, int page); 
	int searchActivityAreaPage(String hql , int family ); 
	
	List<TzzActivityArea> searchByName( String hql,String name,int page);
	int searchByNameNum( String hql,String name );
	List<TzzActivityArea> getindexList(String hql, int page);
	Integer getindexListCount(String hql); 
}
