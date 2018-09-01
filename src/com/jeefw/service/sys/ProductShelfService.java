package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductShelf;
import com.jeefw.model.sys.ProductWarehouseCount;

import core.service.Service;
import core.support.QueryResult;

/**
 * 货架商品信息的业务逻辑层的接口
 * @ 
 */
public interface ProductShelfService extends Service<ProductShelf> {

	List<ProductShelf> queryProductShelfWithSubList(List<ProductShelf> resultList);

	ProductWarehouseCount doCountProductWarehouse(Integer allianceId);

	void updateProductOnShelfNum(String onShelfId, Long num);

	QueryResult selectProductShelfByParam(ProductShelf productShelf);

	List<ProductShelf> queryProductShelfListByIdIn(List<Long> productIds);

}
