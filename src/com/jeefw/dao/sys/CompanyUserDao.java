package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.CompanyUser;

import core.dao.Dao;


   /**
    * CompanyUser数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface CompanyUserDao extends  Dao<CompanyUser>{
	
	List<CompanyUser> getUserByids(List <Integer>ids);

}
