package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeBaseDao;
import com.jeefw.model.sys.ResumeBase;

import core.dao.BaseDao;

/**
 * 简历基本信息的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeBaseDaoImpl extends BaseDao<ResumeBase> implements ResumeBaseDao {

	public ResumeBaseDaoImpl() {
		super(ResumeBase.class);
	}

}
