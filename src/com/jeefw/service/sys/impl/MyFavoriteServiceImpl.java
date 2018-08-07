package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.MyFavoriteDao;
import com.jeefw.model.sys.MyFavorite;
import com.jeefw.service.sys.MyFavoriteService;

import core.service.BaseService;

/**
 * 收藏简历的业务逻辑层的实现
 * @ 
 */
@Service
public class MyFavoriteServiceImpl extends BaseService<MyFavorite> implements MyFavoriteService {

	private MyFavoriteDao myFavoriteDao;

	@Resource
	public void setMyFavoriteDao(MyFavoriteDao myFavoriteDao) {
		this.myFavoriteDao = myFavoriteDao;
		this.dao = myFavoriteDao;
	}

}
