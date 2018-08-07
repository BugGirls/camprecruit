package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeSkillDao;
import com.jeefw.model.sys.ResumeSkill;
import com.jeefw.service.sys.ResumeSkillService;

import core.service.BaseService;

/**
 * 简历技能特长的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeSkillServiceImpl extends BaseService<ResumeSkill> implements ResumeSkillService {

	private ResumeSkillDao resumeSkillDao;

	@Resource
	public void setResumeSkillDao(ResumeSkillDao resumeSkillDao) {
		this.resumeSkillDao = resumeSkillDao;
		this.dao = resumeSkillDao;
	}

}
