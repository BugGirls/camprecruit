package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductPropertyTempDao;
import com.jeefw.model.sys.ProductPropertyTemp;

import core.dao.BaseDao;

/**
 * 商品属性规格模板的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductPropertyTempDaoImpl extends BaseDao<ProductPropertyTemp> implements ProductPropertyTempDao {

	public ProductPropertyTempDaoImpl() {
		super(ProductPropertyTemp.class);
	}

}
