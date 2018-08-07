package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.CompanyNews;

import core.dao.Dao;


   /**
    * CompanyNews数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface CompanyNewsDao extends  Dao<CompanyNews>{
	
	List<CompanyNews> getUserByids(List <Integer>ids);

}
