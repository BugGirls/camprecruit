package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeProductionDao;
import com.jeefw.model.sys.ResumeProduction;

import core.dao.BaseDao;

/**
 * 简历我的作品的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeProductionDaoImpl extends BaseDao<ResumeProduction> implements ResumeProductionDao {

	public ResumeProductionDaoImpl() {
		super(ResumeProduction.class);
	}

}
