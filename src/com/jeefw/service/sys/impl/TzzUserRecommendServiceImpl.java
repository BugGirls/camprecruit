package com.jeefw.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzUserRecommendDao;
import com.jeefw.model.sys.TzzUserRecommend;
import com.jeefw.model.sys.param.TzzUserRecommendParameter;
import com.jeefw.service.sys.TzzUserRecommendService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzUserRecommendServiceImpl extends BaseService<TzzUserRecommend> implements TzzUserRecommendService {

	private TzzUserRecommendDao tzzUserRecommendDao;

	@Resource
	public void setDictDao(TzzUserRecommendDao tzzUserRecommendDao) {
		this.tzzUserRecommendDao = tzzUserRecommendDao;
		this.dao = tzzUserRecommendDao;
	}

	@Override
	public List<TzzUserRecommendParameter> getTopRecommends() {
		// TODO Auto-generated method stub
		return tzzUserRecommendDao.getTopRecommends();
	}


}
