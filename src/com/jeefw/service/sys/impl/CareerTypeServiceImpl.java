package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.CareerTypeDao;
import com.jeefw.model.sys.CareerType;
import com.jeefw.service.sys.CareerTypeService;

import core.service.BaseService;

/**
 * 职位类别的业务逻辑层的实现
 * @ 
 */
@Service
public class CareerTypeServiceImpl extends BaseService<CareerType> implements CareerTypeService {

	private CareerTypeDao careerTypeDao;

	@Resource
	public void setCareerTypeDao(CareerTypeDao careerDao) {
		this.careerTypeDao = careerDao;
		this.dao = careerDao;
	}

}
