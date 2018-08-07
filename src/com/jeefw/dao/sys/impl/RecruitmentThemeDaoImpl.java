package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.RecruitmentThemeDao;
import com.jeefw.model.sys.RecruitmentTheme;

import core.dao.BaseDao;

/**
 * 招聘主题的数据持久层的实现类
 * @ 
 */
@Repository
public class RecruitmentThemeDaoImpl extends BaseDao<RecruitmentTheme> implements RecruitmentThemeDao {

	public RecruitmentThemeDaoImpl() {
		super(RecruitmentTheme.class);
	}

}
