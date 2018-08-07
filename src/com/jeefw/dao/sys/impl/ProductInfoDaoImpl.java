package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductInfoDao;
import com.jeefw.model.sys.ProductInfo;

import core.dao.BaseDao;

/**
 * 商品信息的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductInfoDaoImpl extends BaseDao<ProductInfo> implements ProductInfoDao {

	public ProductInfoDaoImpl() {
		super(ProductInfo.class);
	}

}
