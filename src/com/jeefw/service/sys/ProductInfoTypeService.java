package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductInfoType;

import core.service.Service;

/**
 * 商品类型的业务逻辑层的接口
 * @ 
 */
public interface ProductInfoTypeService extends Service<ProductInfoType> {

	List<ProductInfoType> queryProductInfoTypeWithSubList(List<ProductInfoType> resultList);
 
}
