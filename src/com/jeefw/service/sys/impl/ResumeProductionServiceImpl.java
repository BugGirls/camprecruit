package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeProductionDao;
import com.jeefw.model.sys.ResumeProduction;
import com.jeefw.service.sys.ResumeProductionService;

import core.service.BaseService;

/**
 * 简历我的作品的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeProductionServiceImpl extends BaseService<ResumeProduction> implements ResumeProductionService {

	private ResumeProductionDao resumeProductionDao;

	@Resource
	public void setResumeProductionDao(ResumeProductionDao resumeProductionDao) {
		this.resumeProductionDao = resumeProductionDao;
		this.dao = resumeProductionDao;
	}

}
