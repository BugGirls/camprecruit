package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzUser;

import core.dao.Dao;


   /**
    * TzzUser数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface TzzUserDao extends  Dao<TzzUser>{
	
	List<TzzUser> getUserByids(List <Integer>ids);

}
