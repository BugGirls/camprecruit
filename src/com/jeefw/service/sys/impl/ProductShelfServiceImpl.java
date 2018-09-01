package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.jeefw.model.sys.ProductInfo;
import core.support.QueryResult;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductShelfDao;
import com.jeefw.model.sys.ProductShelf;
import com.jeefw.model.sys.ProductWarehouseCount;
import com.jeefw.service.sys.ProductShelfService;

import core.service.BaseService;
import org.springframework.util.StringUtils;

/**
 * 货架商品类型的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductShelfServiceImpl extends BaseService<ProductShelf> implements ProductShelfService {

	private ProductShelfDao productShelfDao;

	@Resource
	public void setProductShelfDao(ProductShelfDao productShelfDao) {
		this.productShelfDao = productShelfDao;
		this.dao = productShelfDao;
	}

	@Override
	public List<ProductShelf> queryProductShelfWithSubList(List<ProductShelf> resultList) {
		List<ProductShelf> dictList = new ArrayList<ProductShelf>();
		for (ProductShelf entity : resultList) {
			ProductShelf datail = new ProductShelf();
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
		Integer productTotalTypeNum = productShelfDao.doCountProductWarehouseCount(allianceId);
		//商品总数
		Integer productTotalNum = productShelfDao.doCountProductTotalNum(allianceId);
		//进价总额
		Float adviceTotalAmount = productShelfDao.doCountAdviceTotalAmount(allianceId);
		//售价总额
		Float saleTotalAmount = productShelfDao.doCountSaleTotalAmount(allianceId);
		
		ProductWarehouseCount warehouseCount = new ProductWarehouseCount();
		warehouseCount.setProductTotalTypeNum(productTotalTypeNum+"");
		warehouseCount.setProductTotalNum(productTotalNum+"");
		warehouseCount.setAdviceTotalAmount(adviceTotalAmount+"");
		warehouseCount.setSaleTotalAmount(saleTotalAmount+"");
		warehouseCount.setAllianceId(allianceId);
		return warehouseCount;
	}

	@Override
	public void updateProductOnShelfNum(String onShelfId, Long num) {
		productShelfDao.updateProductOnShelfNum(onShelfId,num);
	}

	@Override
	public QueryResult selectProductShelfByParam(ProductShelf productShelf) {
		if (productShelf == null) {
			return null;
		}

		return productShelfDao.selectProductShelfByParam(productShelf);
	}

	@Override
	public List<ProductShelf> queryProductShelfListByIdIn(List<Long> productIds) {
		return productShelfDao.queryProductShelfListByIdIn(productIds);
	}

}
