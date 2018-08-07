package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.PositionDao;
import com.jeefw.model.sys.Position;
import com.jeefw.service.sys.PositionService;

import core.service.BaseService;

/**
 * 职位的业务逻辑层的实现
 * @ 
 */
@Service
public class PositionServiceImpl extends BaseService<Position> implements PositionService {

	private PositionDao careerDao;

	@Resource
	public void setCareerDao(PositionDao careerDao1) {
		this.careerDao = careerDao1;
		this.dao = careerDao1;
	}

}
