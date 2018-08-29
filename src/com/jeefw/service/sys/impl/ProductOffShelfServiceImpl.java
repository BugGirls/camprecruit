package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductOffShelfDao;
import com.jeefw.model.sys.ProductOffShelf;
import com.jeefw.model.sys.ProductWarehouseCount;
import com.jeefw.service.sys.ProductOffShelfService;

import core.service.BaseService;

/**
 * 货架商品类型的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductOffShelfServiceImpl extends BaseService<ProductOffShelf> implements ProductOffShelfService {

	private ProductOffShelfDao productOffShelfDao;

	@Resource
	public void setProductOffShelfDao(ProductOffShelfDao productOffShelfDao) {
		this.productOffShelfDao = productOffShelfDao;
		this.dao = productOffShelfDao;
	}

	public List<ProductOffShelf> queryProductShelfWithSubList(List<ProductOffShelf> resultList) {
		List<ProductOffShelf> dictList = new ArrayList<ProductOffShelf>();
		for (ProductOffShelf entity : resultList) {
			ProductOffShelf datail = new ProductOffShelf();
			datail.setId(entity.getId());
			datail.setOffShelfNo(entity.getOffShelfNo());
			datail.setOnShelfId(entity.getOnShelfId());
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
			datail.setShelfLocation(entity.getShelfLocation());
			datail.setProductionDate(entity.getProductionDate());
			datail.setAllianceId(entity.getAllianceId());
			dictList.add(datail);
		}
		return dictList;
	}

	@Override
	public ProductWarehouseCount doCountProductWarehouse(Integer allianceId) {
		//商品种类总数
		Integer productTotalTypeNum = productOffShelfDao.doCountProductWarehouseCount(allianceId);
		//商品总数
		Integer productTotalNum = productOffShelfDao.doCountProductTotalNum(allianceId);
		//进价总额
		Float adviceTotalAmount = productOffShelfDao.doCountAdviceTotalAmount(allianceId);
		//售价总额
		Float saleTotalAmount = productOffShelfDao.doCountSaleTotalAmount(allianceId);
		
		ProductWarehouseCount warehouseCount = new ProductWarehouseCount();
		warehouseCount.setProductTotalTypeNum(productTotalTypeNum+"");
		warehouseCount.setProductTotalNum(productTotalNum+"");
		warehouseCount.setAdviceTotalAmount(adviceTotalAmount+"");
		warehouseCount.setSaleTotalAmount(saleTotalAmount+"");
		warehouseCount.setAllianceId(allianceId);
		return warehouseCount;
	}

}
