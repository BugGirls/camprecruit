package com.jeefw.dao.sys;


import com.jeefw.model.sys.ProductOffShelf;

import core.dao.Dao;

/**
 * 货架商品信息的数据持久层的接口
 * @ 
 */
public interface ProductOffShelfDao extends Dao<ProductOffShelf> {

	Integer doCountProductWarehouseCount(Integer allianceId);

	Integer doCountProductTotalNum(Integer allianceId);

	Float doCountAdviceTotalAmount(Integer allianceId);

	Float doCountSaleTotalAmount(Integer allianceId);

}
