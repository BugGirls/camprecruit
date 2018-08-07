package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.RecruitmentThemeDao;
import com.jeefw.model.sys.RecruitmentTheme;
import com.jeefw.service.sys.RecruitmentThemeService;

import core.service.BaseService;

/**
 * 招聘主题的业务逻辑层的实现
 * @ 
 */
@Service
public class RecruitmentThemeServiceImpl extends BaseService<RecruitmentTheme> implements RecruitmentThemeService {

	private RecruitmentThemeDao recruitmentThemeDao;

	@Resource
	public void setRecruitmentThemeDao(RecruitmentThemeDao recruitmentThemeDao) {
		this.recruitmentThemeDao = recruitmentThemeDao;
		this.dao = recruitmentThemeDao;
	}

}
