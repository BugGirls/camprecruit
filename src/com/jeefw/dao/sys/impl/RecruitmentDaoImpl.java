package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.RecruitmentDao;
import com.jeefw.model.sys.Recruitment;

import core.dao.BaseDao;

/**
 * 主题招聘的数据持久层的实现类
 * @ 
 */
@Repository
public class RecruitmentDaoImpl extends BaseDao<Recruitment> implements RecruitmentDao {

	public RecruitmentDaoImpl() {
		super(Recruitment.class);
	}

}
