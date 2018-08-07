package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductPropertyTemp;

import core.service.Service;

/**
 *  商品属性规格模板的业务逻辑层的接口
 * @ 
 */
public interface ProductPropertyTempService extends Service<ProductPropertyTemp> {

	List<ProductPropertyTemp> queryProductPropertyTempWithSubList(List<ProductPropertyTemp> resultList);
 
}
