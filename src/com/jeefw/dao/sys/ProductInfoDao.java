package com.jeefw.dao.sys;


import com.jeefw.model.sys.ProductInfo;

import core.dao.Dao;

import java.util.List;

/**
 * 商品信息的数据持久层的接口
 * @ 
 */
public interface ProductInfoDao extends Dao<ProductInfo> {

    List<ProductInfo> queryProductListByNoIn(List<String> productNoList);
}
