package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.RecruitmentDao;
import com.jeefw.model.sys.Recruitment;
import com.jeefw.service.sys.RecruitmentService;

import core.service.BaseService;

/**
 * 主题招聘的业务逻辑层的实现
 * @ 
 */
@Service
public class RecruitmentServiceImpl extends BaseService<Recruitment> implements RecruitmentService {

	private RecruitmentDao recruitmentDao;

	@Resource
	public void setRecruitmentDao(RecruitmentDao recruitmentDao) {
		this.recruitmentDao = recruitmentDao;
		this.dao = recruitmentDao;
	}

}
