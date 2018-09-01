package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductInfo;

import core.service.Service;

/**
 * 商品信息的业务逻辑层的接口
 * @ 
 */
public interface ProductInfoService extends Service<ProductInfo> {

	List<ProductInfo> queryProductInfoWithSubList(List<ProductInfo> resultList);

	List<ProductInfo> queryProductListByIdIn(List<Long> productIds);
 
}
