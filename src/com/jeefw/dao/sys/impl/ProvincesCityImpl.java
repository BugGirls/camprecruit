package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;
import com.jeefw.dao.sys.ProvincesCityDao;
import com.jeefw.model.sys.ProvincesCity;

import core.dao.BaseDao;

/**
 * 省市的数据持久层的实现类
 * @ 
 */
@Repository
public class ProvincesCityImpl extends BaseDao<ProvincesCity> implements ProvincesCityDao {

	public ProvincesCityImpl() {
		super(ProvincesCity.class);
	}

}
