package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzCooperationDao;
import com.jeefw.model.sys.TzzCooperation;
import com.jeefw.service.sys.TzzCooperationService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzCooperationServiceImpl extends BaseService<TzzCooperation> implements TzzCooperationService {

	private TzzCooperationDao tzzCooperationDao;

	@Resource
	public void setTzzCooperationDao(TzzCooperationDao tzzCooperationDao) {
		this.tzzCooperationDao = tzzCooperationDao;
		this.dao = tzzCooperationDao;
	}


}
