package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;
 

  
import com.jeefw.dao.sys.ZizengDao; 
import com.jeefw.model.sys.Zizeng;

import core.dao.BaseDao;

/**
 * zizeng 的数据持久层的实现类
 * @ 
 */
@Repository
public class ZizengdaoImpl extends BaseDao<Zizeng> implements ZizengDao {

	public ZizengdaoImpl() {
		super(Zizeng.class);
	}

}

