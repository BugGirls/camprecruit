package com.jeefw.dao.sys;



import java.util.List;

import com.jeefw.model.sys.TzzUserRecommend;
import com.jeefw.model.sys.param.TzzUserRecommendParameter;

import core.dao.Dao;


   /**
    * TzzUserRecommend数据持久层的接口 
    */ 
public interface TzzUserRecommendDao extends  Dao<TzzUserRecommend>{

	List<TzzUserRecommendParameter> getTopRecommends();

}
