package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProvincesCityDao;
import com.jeefw.model.sys.ProvincesCity;
import com.jeefw.service.sys.ProvincesCityService;

import core.service.BaseService;

/**
 * 城市的业务逻辑层的实现
 * @ 
 */
@Service
public class ProvincesCityServiceImpl extends BaseService<ProvincesCity> implements ProvincesCityService {

	private ProvincesCityDao provincesCityDao;

	@Resource
	public void setProvincesCityDao(ProvincesCityDao provincesCityDao) {
		this.provincesCityDao = provincesCityDao;
		this.dao = provincesCityDao;
	}

}
