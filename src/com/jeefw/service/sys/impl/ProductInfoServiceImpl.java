package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductInfoDao;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.service.sys.ProductInfoService;

import core.service.BaseService;

/**
 * 商品类型的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductInfoServiceImpl extends BaseService<ProductInfo> implements ProductInfoService {

	private ProductInfoDao productInfoDao;

	@Resource
	public void setProductInfoDao(ProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
		this.dao = productInfoDao;
	}

	public List<ProductInfo> queryProductInfoWithSubList(List<ProductInfo> resultList) {
		List<ProductInfo> dictList = new ArrayList<ProductInfo>();
		for (ProductInfo entity : resultList) {
			ProductInfo dict = new ProductInfo();
			dict.setId(entity.getId());
			dict.setName(entity.getName());
			dict.setNo(entity.getNo());
			dict.setBarCode(entity.getBarCode());
			dict.setAdvicePrice(entity.getAdvicePrice());
			dict.setBigCategoryNo(entity.getBigCategoryNo());
			dict.setBigCategoryName(entity.getBigCategoryName());
			dict.setSmallCategoryNo(entity.getSmallCategoryNo());
			dict.setSmallCategoryName(entity.getSmallCategoryName());
			dict.setProductPropertyTempNo(entity.getProductPropertyTempNo());
			dict.setImage(entity.getImage());
			dict.setImage1(entity.getImage1());
			dict.setImage2(entity.getImage2());
			dict.setImage3(entity.getImage3());
			dict.setContent(entity.getContent());
			dict.setBrand(entity.getBrand());
			dictList.add(dict);
		}
		return dictList;
	}

}
