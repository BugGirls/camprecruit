package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.CompanyInfo;

import core.dao.Dao;


   /**
    * CompanyInfo数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface CompanyInfoDao extends  Dao<CompanyInfo>{
	
	List<CompanyInfo> getUserByids(List <Integer>ids);

}
