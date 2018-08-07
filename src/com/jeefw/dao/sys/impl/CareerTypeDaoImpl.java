package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;
import com.jeefw.dao.sys.CareerTypeDao;
import com.jeefw.model.sys.CareerType;
import core.dao.BaseDao;

/**
 * 职位类别的数据持久层的实现类
 * @ 
 */
@Repository
public class CareerTypeDaoImpl extends BaseDao<CareerType> implements CareerTypeDao {

	public CareerTypeDaoImpl() {
		super(CareerType.class);
	}

}
