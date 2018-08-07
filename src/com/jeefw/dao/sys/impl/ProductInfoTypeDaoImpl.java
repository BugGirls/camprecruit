package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductInfoTypeDao;
import com.jeefw.model.sys.ProductInfoType;

import core.dao.BaseDao;

/**
 * 商品类型的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductInfoTypeDaoImpl extends BaseDao<ProductInfoType> implements ProductInfoTypeDao {

	public ProductInfoTypeDaoImpl() {
		super(ProductInfoType.class);
	}

}
