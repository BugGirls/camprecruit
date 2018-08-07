package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeExpectationDao;
import com.jeefw.model.sys.ResumeExpectation;

import core.dao.BaseDao;

/**
 * 简历期望工作的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeExpectationDaoImpl extends BaseDao<ResumeExpectation> implements ResumeExpectationDao {

	public ResumeExpectationDaoImpl() {
		super(ResumeExpectation.class);
	}

}
