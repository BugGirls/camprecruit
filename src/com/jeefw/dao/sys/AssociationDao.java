package com.jeefw.dao.sys;



import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.Association;

import core.dao.Dao;


   /**
    * Association数据持久层的接口 
    * 
    */ 
public interface AssociationDao extends  Dao<Association>{
	
	List<Association> getAssociationListByIds(List<Integer>ids);
	List<Association> queryHotByorder(String hql );
	List<Association> queryAllByorder(String hql ,int page);
	int pageAllByorder(String hql );
	List<Association> searchAssociation(String hql , int family, int page); 
	int searchAssociationPage(String hql , int family ); 
	
	List<Association> searchByName( String hql,String name,int page);
	int searchByNameNum( String hql,String name );
	List<Association> getindexList(String hql, int page);
	Integer getindexListCount(String hql);
	Map<Integer, String> getAssociationMap();
}
