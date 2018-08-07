package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ResumeSkillDao;
import com.jeefw.model.sys.ResumeSkill;

import core.dao.BaseDao;

/**
 * 简历技能特长的数据持久层的实现类
 * @ 
 */
@Repository
public class ResumeSkillDaoImpl extends BaseDao<ResumeSkill> implements ResumeSkillDao {

	public ResumeSkillDaoImpl() {
		super(ResumeSkill.class);
	}

}
