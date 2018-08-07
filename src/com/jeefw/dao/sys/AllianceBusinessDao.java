package com.jeefw.dao.sys;



import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.AllianceBusiness;

import core.dao.Dao;


   /**
    * AllianceBusiness数据持久层的接口 
    * 
    */ 
public interface AllianceBusinessDao extends  Dao<AllianceBusiness>{
	
	List<AllianceBusiness> getAllianceBusinessListByIds(List<Integer>ids);
	List<AllianceBusiness> queryHotByorder(String hql );
	List<AllianceBusiness> queryAllByorder(String hql ,int page);
	int pageAllByorder(String hql );
	List<AllianceBusiness> searchAllianceBusiness(String hql , int family, int page); 
	int searchAllianceBusinessPage(String hql , int family ); 
	
	List<AllianceBusiness> searchByName( String hql,String name,int page);
	int searchByNameNum( String hql,String name );
	List<AllianceBusiness> getindexList(String hql, int page);
	Integer getindexListCount(String hql);
	Map<Integer, String> getAllianceBusinessMap();
}
