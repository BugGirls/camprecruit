package com.jeefw.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 







import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzUserSetvipDao;
import com.jeefw.model.sys.TzzDictionary;  
import com.jeefw.model.sys.TzzUserSetvip;
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzUserSetvipService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzUserSetvipServiceImpl extends BaseService<TzzUserSetvip> implements TzzUserSetvipService {

	private TzzUserSetvipDao tzzUserSetvipDao;

	@Resource
	public void setTzzDictionaryDao(TzzUserSetvipDao tzzUserSetvipDao) {
		this.tzzUserSetvipDao = tzzUserSetvipDao;
		this.dao = tzzUserSetvipDao;
	}
 
	@Override
	public void deleteTzzusersetvipById(int tdId) {
	//	tzzUserSetvipDao.
		
	}
 
	@Override
	public List<TzzUserSetvip> getbyMinamounDesc() {
		return tzzUserSetvipDao.querySensorList();
	}

	 
	 

}
