package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductWarehouse;
import com.jeefw.model.sys.ProductWarehouseCount;

import core.service.Service;

/**
 * 库存商品的业务逻辑层的接口
 * @ 
 */
public interface ProductWarehouseService extends Service<ProductWarehouse> {

	List<ProductWarehouse> queryProductWarehouseWithSubList(List<ProductWarehouse> resultList);

	/**
	 * 统计库存商品信息
	 * @param allianceId
	 * @return
	 */
	ProductWarehouseCount doCountProductWarehouse(Integer allianceId);

	/**
	 * 更新库存数量
	 * @param productWarehouseId
	 * @param num
	 */
	void updateProductWarehouseNum(String productWarehouseId, Long num);

	List<ProductWarehouse> findByNoAndAllianceId(String productNo, String allianceId);
 
}
