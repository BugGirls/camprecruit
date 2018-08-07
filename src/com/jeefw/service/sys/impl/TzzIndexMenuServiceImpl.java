package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzIndexMenuDao;
import com.jeefw.model.sys.TzzIndexMenu;
import com.jeefw.service.sys.TzzIndexMenuService;

import core.service.BaseService;

/**
 * 主页功能列表的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzIndexMenuServiceImpl extends BaseService<TzzIndexMenu> implements TzzIndexMenuService {

	private TzzIndexMenuDao tzzIndexMenuDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	public void setTzzIndexMenuDao(TzzIndexMenuDao indexMenuDao) {
		this.tzzIndexMenuDao = indexMenuDao;
		this.dao = indexMenuDao;
	}

}
