package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductInfoTypeDao;
import com.jeefw.model.sys.ProductInfoType;
import com.jeefw.service.sys.ProductInfoTypeService;

import core.service.BaseService;

/**
 * 商品类型的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductInfoTypeServiceImpl extends BaseService<ProductInfoType> implements ProductInfoTypeService {

	private ProductInfoTypeDao productInfoTypeDao;

	@Resource
	public void setProductInfoTypeDao(ProductInfoTypeDao productInfoTypeDao) {
		this.productInfoTypeDao = productInfoTypeDao;
		this.dao = productInfoTypeDao;
	}

	public List<ProductInfoType> queryProductInfoTypeWithSubList(List<ProductInfoType> resultList) {
		List<ProductInfoType> dictList = new ArrayList<ProductInfoType>();
		for (ProductInfoType entity : resultList) {
			ProductInfoType dict = new ProductInfoType();
			dict.setId(entity.getId());
			dict.setTypeKey(entity.getTypeKey());
			dict.setTypeValue(entity.getTypeValue());
			dict.setSequence(entity.getSequence());
			dict.setParentTypekey(entity.getParentTypekey());
			dictList.add(dict);
		}
		return dictList;
	}

}
