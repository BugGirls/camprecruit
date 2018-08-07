package com.jeefw.dao.sys;



import java.util.List;
 

import com.jeefw.model.sys.TzzAnswer; 

import core.dao.Dao;


   /**
    * TzzAnswer数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzAnswerDao extends  Dao<TzzAnswer>{
	// 生成信息的索引
	void indexingTzzAnswer();

	// 全文检索信息
	List<TzzAnswer> queryByTzzAnswerName(String name);

}
