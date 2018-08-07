package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.TzzUserRecommend;
import com.jeefw.model.sys.param.TzzUserRecommendParameter;

import core.service.Service;

/**
 * 字典的业务逻辑层的接口
 * @ 
 */
public interface TzzUserRecommendService extends Service<TzzUserRecommend> {

	List<TzzUserRecommendParameter> getTopRecommends();
	
}
