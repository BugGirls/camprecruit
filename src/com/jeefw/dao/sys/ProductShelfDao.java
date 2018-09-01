package com.jeefw.dao.sys;


import com.jeefw.model.sys.ProductInfo;
import com.jeefw.model.sys.ProductShelf;

import core.dao.Dao;
import core.support.QueryResult;

import java.util.List;

/**
 * 货架商品信息的数据持久层的接口
 * @ 
 */
public interface ProductShelfDao extends Dao<ProductShelf> {

	Integer doCountProductWarehouseCount(Integer allianceId);

	Integer doCountProductTotalNum(Integer allianceId);

	Float doCountAdviceTotalAmount(Integer allianceId);

	Float doCountSaleTotalAmount(Integer allianceId);

	void updateProductOnShelfNum(String onShelfId, Long num);

	QueryResult selectProductShelfByParam(ProductShelf productShelf);

	List<ProductShelf> queryProductShelfListByIdIn(List<Long> productShelfIds);

}
