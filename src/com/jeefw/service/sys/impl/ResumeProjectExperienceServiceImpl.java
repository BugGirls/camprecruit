package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeProjectExperienceDao;
import com.jeefw.model.sys.ResumeProjectExperience;
import com.jeefw.service.sys.ResumeProjectExperienceService;

import core.service.BaseService;

/**
 * 简历项目经验的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeProjectExperienceServiceImpl extends BaseService<ResumeProjectExperience> implements ResumeProjectExperienceService {

	private ResumeProjectExperienceDao projectExperienceDao;

	@Resource
	public void setResumeProjectExperienceDao(ResumeProjectExperienceDao projectExperienceDao) {
		this.projectExperienceDao = projectExperienceDao;
		this.dao = projectExperienceDao;
	}

}
