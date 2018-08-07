package com.jeefw.dao.sys;



import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.CooperationColleges;

import core.dao.Dao;


   /**
    * CooperationColleges数据持久层的接口 
    * 
    */ 
public interface CooperationCollegesDao extends  Dao<CooperationColleges>{
	
	List<CooperationColleges> getCooperationCollegesListByIds(List<Integer>ids);
	List<CooperationColleges> queryHotByorder(String hql );
	List<CooperationColleges> queryAllByorder(String hql ,int page);
	int pageAllByorder(String hql );
	List<CooperationColleges> searchCooperationColleges(String hql , int family, int page); 
	int searchCooperationCollegesPage(String hql , int family ); 
	
	List<CooperationColleges> searchByName( String hql,String name,int page);
	int searchByNameNum( String hql,String name );
	List<CooperationColleges> getindexList(String hql, int page);
	Integer getindexListCount(String hql);
	Map<Integer, String> getCooperationCollegesMap();
}
