package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductShelf;
import com.jeefw.model.sys.ProductWarehouseCount;

import core.service.Service;

/**
 * 货架商品信息的业务逻辑层的接口
 * @ 
 */
public interface ProductShelfService extends Service<ProductShelf> {

	List<ProductShelf> queryProductShelfWithSubList(List<ProductShelf> resultList);

	ProductWarehouseCount doCountProductWarehouse(Integer allianceId);

	void updateProductOnShelfNum(String onShelfId, Long num);

	List<ProductShelf> selectProductShelfByParam(ProductShelf productShelf);
 
}
