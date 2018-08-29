package com.jeefw.dao.sys;


import java.util.List;

import com.jeefw.model.sys.ProductWarehouse;

import core.dao.Dao;

/**
 * 库存商品的数据持久层的接口
 * @ 
 */
public interface ProductWarehouseDao extends Dao<ProductWarehouse> {
	/**
	 * 商品统计
	 * @param allianceId
	 * @return
	 */
	Integer doCountProductWarehouseCount(Integer allianceId);

	/**
	 * 商品总数
	 * @param allianceId
	 * @return
	 */
	Integer doCountProductTotalNum(Integer allianceId);

	/**
	 * 进价总额
	 * @param allianceId
	 * @return
	 */
	Float doCountAdviceTotalAmount(Integer allianceId);

	/**
	 * 售价总额
	 * @param allianceId
	 * @return
	 */
	Float doCountSaleTotalAmount(Integer allianceId);

	/**
	 * 更新库存数量
	 * @param productWarehouseId
	 * @param num
	 */
	void updateProductWarehouseNum(String productWarehouseId, Long num);
	
	List<ProductWarehouse> findByNoAndAllianceId(String productNo, String allianceId);

}
