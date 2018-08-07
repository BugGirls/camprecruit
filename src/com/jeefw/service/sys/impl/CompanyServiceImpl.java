package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.CompanyDao;
import com.jeefw.model.sys.Company;
import com.jeefw.service.sys.CompanyService;

import core.service.BaseService;

/**
 * 公司的业务逻辑层的实现
 * @ 
 */
@Service
public class CompanyServiceImpl extends BaseService<Company> implements CompanyService {

	private CompanyDao companyDao;

	@Resource
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
		this.dao = companyDao;
	}

}
