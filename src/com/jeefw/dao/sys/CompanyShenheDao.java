package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.CompanyShenhe;

import core.dao.Dao;


   /**
    * CompanyShenhe数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface CompanyShenheDao extends  Dao<CompanyShenhe>{
	
	List<CompanyShenhe> getUserByids(List <Integer>ids);

}
