package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeExpectationDao;
import com.jeefw.model.sys.ResumeExpectation;
import com.jeefw.service.sys.ResumeExpectationService;

import core.service.BaseService;

/**
 * 简历期望工作的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeExpectationServiceImpl extends BaseService<ResumeExpectation> implements ResumeExpectationService {

	private ResumeExpectationDao resumeExpectationDao;

	@Resource
	public void setResumeExpectationDao(ResumeExpectationDao resumeExpectationDao) {
		this.resumeExpectationDao = resumeExpectationDao;
		this.dao = resumeExpectationDao;
	}

}
