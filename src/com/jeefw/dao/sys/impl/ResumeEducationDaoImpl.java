package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeEducationDao;
import com.jeefw.model.sys.ResumeEducation;

import core.dao.BaseDao;

/**
 * 简历教育经历的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeEducationDaoImpl extends BaseDao<ResumeEducation> implements ResumeEducationDao {

	public ResumeEducationDaoImpl() {
		super(ResumeEducation.class);
	}

}
