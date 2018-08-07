package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeProjectExperienceDao;
import com.jeefw.model.sys.ResumeProjectExperience;

import core.dao.BaseDao;

/**
 * 简历项目经验的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeProjectExperienceDaoImpl extends BaseDao<ResumeProjectExperience> implements ResumeProjectExperienceDao {

	public ResumeProjectExperienceDaoImpl() {
		super(ResumeProjectExperience.class);
	}

}
