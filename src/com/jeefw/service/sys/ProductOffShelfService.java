package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.ProductOffShelf;
import com.jeefw.model.sys.ProductWarehouseCount;

import core.service.Service;

/**
 * 下架商品信息的业务逻辑层的接口
 * @ 
 */
public interface ProductOffShelfService extends Service<ProductOffShelf> {

	List<ProductOffShelf> queryProductShelfWithSubList(List<ProductOffShelf> resultList);

	ProductWarehouseCount doCountProductWarehouse(Integer allianceId);
 
}
