package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeEducationDao;
import com.jeefw.model.sys.ResumeEducation;
import com.jeefw.service.sys.ResumeEducationService;

import core.service.BaseService;

/**
 * 简历教育经历的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeEducationServiceImpl extends BaseService<ResumeEducation> implements ResumeEducationService {

	private ResumeEducationDao resumeEducationDao;

	@Resource
	public void setResumeEducationDao(ResumeEducationDao resumeEducationDao) {
		this.resumeEducationDao = resumeEducationDao;
		this.dao = resumeEducationDao;
	}

}
