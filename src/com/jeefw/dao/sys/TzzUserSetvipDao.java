package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzUserSetvip;

import core.dao.Dao;


   /**
    * TzzUserSetvip数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface TzzUserSetvipDao extends  Dao<TzzUserSetvip>{
	 List<TzzUserSetvip> querySensorList();
}
