package com.jeefw.dao.sys;



import com.jeefw.model.sys.TzzUserEvaluation;

import core.dao.Dao;


   /**
    * TzzUserEvaluation数据持久层的接口 
    * Fri Sep 18 09:59:34 CST 2015  tudou
    */ 
public interface TzzUserEvaluationDao extends  Dao<TzzUserEvaluation>{
	
	int getUserEvaluationbyProductId(int productId);

}
