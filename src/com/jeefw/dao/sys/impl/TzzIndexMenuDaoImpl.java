package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzIndexMenuDao;
import com.jeefw.model.sys.TzzIndexMenu;

import core.dao.BaseDao;


   /**
    * TzzIndexMenu的数据持久层的实现类 
    * 2017/11/27 10:31:31
    */ 
@Repository
public class TzzIndexMenuDaoImpl extends  BaseDao<TzzIndexMenu> implements TzzIndexMenuDao {

	public TzzIndexMenuDaoImpl() {
		super(TzzIndexMenu.class);
	}

}

