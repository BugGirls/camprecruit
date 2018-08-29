package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductWarehouseDao;
import com.jeefw.model.sys.ProductWarehouse;
import com.jeefw.model.sys.ProductWarehouseCount;
import com.jeefw.service.sys.ProductWarehouseService;

import core.service.BaseService;

/**
 * 库存商品的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductWarehouseServiceImpl extends BaseService<ProductWarehouse> implements ProductWarehouseService {

	private ProductWarehouseDao productWarehouseDao;

	@Resource
	public void setProductWarehouseDao(ProductWarehouseDao productWarehouseDao) {
		this.productWarehouseDao = productWarehouseDao;
		this.dao = productWarehouseDao;
	}

	public List<ProductWarehouse> queryProductWarehouseWithSubList(List<ProductWarehouse> resultList) {
		List<ProductWarehouse> dictList = new ArrayList<ProductWarehouse>();
		for (ProductWarehouse entity : resultList) {
			ProductWarehouse datail = new ProductWarehouse();
			datail.setId(entity.getId());
			datail.setIntoWarehouseRecordNo(entity.getIntoWarehouseRecordNo());
			datail.setProductNo(entity.getProductNo());
			datail.setProductName(entity.getProductName());
			datail.setProductBarCode(entity.getProductBarCode());
			datail.setNum(entity.getNum());
			datail.setSalePrice(entity.getSalePrice());
			datail.setAdvicePrice(entity.getAdvicePrice());
			datail.setBigCategoryName(entity.getBigCategoryName());
			datail.setBigCategoryNo(entity.getBigCategoryNo());
			datail.setSmallCategoryName(entity.getSmallCategoryName());
			datail.setSmallCategoryNo(entity.getSmallCategoryNo());
			datail.setProductPropertyTempNo(entity.getProductPropertyTempNo());
			datail.setImage(entity.getImage());
			datail.setImage1(entity.getImage1());
			datail.setImage2(entity.getImage2());
			datail.setImage3(entity.getImage3());
			datail.setContent(entity.getContent());
			datail.setBrand(entity.getBrand());
			datail.setStorageLocation(entity.getStorageLocation());
			datail.setProductionDate(entity.getProductionDate());
			datail.setAllianceId(entity.getAllianceId());
			dictList.add(datail);
		}
		return dictList;
	}

	@Override
	public ProductWarehouseCount doCountProductWarehouse(Integer allianceId) {
		//商品种类总数
		Integer productTotalTypeNum = productWarehouseDao.doCountProductWarehouseCount(allianceId);
		//商品总数
		Integer productTotalNum = productWarehouseDao.doCountProductTotalNum(allianceId);
		//进价总额
		Float adviceTotalAmount = productWarehouseDao.doCountAdviceTotalAmount(allianceId);
		//售价总额
		Float saleTotalAmount = productWarehouseDao.doCountSaleTotalAmount(allianceId);
		
		ProductWarehouseCount warehouseCount = new ProductWarehouseCount();
		warehouseCount.setProductTotalTypeNum(productTotalTypeNum+"");
		warehouseCount.setProductTotalNum(productTotalNum+"");
		warehouseCount.setAdviceTotalAmount(adviceTotalAmount+"");
		warehouseCount.setSaleTotalAmount(saleTotalAmount+"");
		warehouseCount.setAllianceId(allianceId);
		return warehouseCount;
	}

	@Override
	public void updateProductWarehouseNum(String productWarehouseId, Long num) {
		productWarehouseDao.updateProductWarehouseNum(productWarehouseId,num);
	}

	@Override
	public List<ProductWarehouse> findByNoAndAllianceId(String productNo, String allianceId) {
		return productWarehouseDao.findByNoAndAllianceId(productNo, allianceId);
	}

}
