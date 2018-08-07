package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.PositionDao;
import com.jeefw.model.sys.Position;
import core.dao.BaseDao;

/**
 * 职位的数据持久层的实现类
 * @ 
 */
@Repository
public class PositionDaoImpl extends BaseDao<Position> implements PositionDao {

	public PositionDaoImpl() {
		super(Position.class);
	}

}
