package com.jeefw.service.sys.impl;
 
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
 
import com.jeefw.dao.sys.ZizengDao; 
import com.jeefw.model.sys.Zizeng; 
import com.jeefw.service.sys.ZizengService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class ZizengServiceImpl extends BaseService<Zizeng> implements ZizengService {

	private ZizengDao zizengDao;

	@Resource
	public void setVideoDao(ZizengDao zizengDao) {
		this.zizengDao = zizengDao;
		this.dao = zizengDao;
	}

	 

}
