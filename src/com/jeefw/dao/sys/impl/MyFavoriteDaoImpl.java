package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.MyFavoriteDao;
import com.jeefw.model.sys.MyFavorite;

import core.dao.BaseDao;

/**
 * 收藏职位的数据持久层的实现类
 * @ 
 */
@Repository
public class MyFavoriteDaoImpl extends BaseDao<MyFavorite> implements MyFavoriteDao {

	public MyFavoriteDaoImpl() {
		super(MyFavorite.class);
	}

}
