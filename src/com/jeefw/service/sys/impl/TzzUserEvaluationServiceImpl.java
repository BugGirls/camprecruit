package com.jeefw.service.sys.impl;
 

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
import com.jeefw.dao.sys.TzzUserEvaluationDao; 
import com.jeefw.model.sys.TzzUserEvaluation; 
import com.jeefw.service.sys.TzzUserEvaluationService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzUserEvaluationServiceImpl extends BaseService<TzzUserEvaluation> implements TzzUserEvaluationService {

	private TzzUserEvaluationDao tzzUserEvaluationDao;

	@Resource
	public void setDictDao(TzzUserEvaluationDao tzzUserEvaluationDao) {
		this.tzzUserEvaluationDao = tzzUserEvaluationDao;
		this.dao = tzzUserEvaluationDao;
	}

	@Override
	public int getUserEvaluationbyProductId(int productId) {
		// TODO Auto-generated method stub
		int count = this.tzzUserEvaluationDao.getUserEvaluationbyProductId(productId);
		return count;
	}



}
