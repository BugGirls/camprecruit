package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.CompanyDao;
import com.jeefw.model.sys.Company;

import core.dao.BaseDao;

/**
 * 公司的数据持久层的实现类
 * @ 
 */
@Repository
public class CompanyDaoImpl extends BaseDao<Company> implements CompanyDao {

	public CompanyDaoImpl() {
		super(Company.class);
	}

}
