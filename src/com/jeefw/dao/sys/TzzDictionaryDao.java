package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzDictionary;

import core.dao.Dao;


   /**
    * TzzDictionary数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzDictionaryDao extends  Dao<TzzDictionary>{
	void deleteTzzDicById(int tdId);
	
	List<TzzDictionary> getDictionaryByids(List<Integer> ids);
}
