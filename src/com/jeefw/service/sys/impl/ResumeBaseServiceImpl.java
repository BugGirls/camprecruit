package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ResumeBaseDao;
import com.jeefw.model.sys.ResumeBase;
import com.jeefw.service.sys.ResumeBaseService;

import core.service.BaseService;

/**
 * 简历基本信息的业务逻辑层的实现
 * @ 
 */
@Service
public class ResumeBaseServiceImpl extends BaseService<ResumeBase> implements ResumeBaseService {

	private ResumeBaseDao resumeBaseDao;

	@Resource
	public void setResumeBaseDao(ResumeBaseDao resumeBaseDao) {
		this.resumeBaseDao = resumeBaseDao;
		this.dao = resumeBaseDao;
	}

}
