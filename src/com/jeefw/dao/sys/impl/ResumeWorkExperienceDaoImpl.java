package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeWorkExperienceDao;
import com.jeefw.model.sys.ResumeWorkExperience;

import core.dao.BaseDao;

/**
 * 简历工作经验的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeWorkExperienceDaoImpl extends BaseDao<ResumeWorkExperience> implements ResumeWorkExperienceDao {

	public ResumeWorkExperienceDaoImpl() {
		super(ResumeWorkExperience.class);
	}

}
