package com.jeefw.service.sys.impl;


import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.jeefw.dao.sys.TzzUserAddressDao;
import com.jeefw.model.sys.TzzUserAddress;
import com.jeefw.service.sys.TzzUserAddressService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzUserAddressServiceImpl extends BaseService<TzzUserAddress> implements TzzUserAddressService {

	private TzzUserAddressDao tzzUserAddressDao;

	@Resource
	public void setDictDao(TzzUserAddressDao tzzUserAddressDao) {
		this.tzzUserAddressDao = tzzUserAddressDao;
		this.dao = tzzUserAddressDao;
	}

	

}
