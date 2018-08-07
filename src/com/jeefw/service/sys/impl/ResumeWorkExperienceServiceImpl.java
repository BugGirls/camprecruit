package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeWorkExperienceDao;
import com.jeefw.model.sys.ResumeWorkExperience;
import com.jeefw.service.sys.ResumeWorkExperienceService;

import core.service.BaseService;

/**
 * 简历工作经验的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeWorkExperienceServiceImpl extends BaseService<ResumeWorkExperience> implements ResumeWorkExperienceService {

	private ResumeWorkExperienceDao workExperienceDao;

	@Resource
	public void setResumeWorkExperienceDao(ResumeWorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
		this.dao = workExperienceDao;
	}

}
