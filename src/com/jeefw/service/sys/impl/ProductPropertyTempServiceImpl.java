package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ProductPropertyTempDao;
import com.jeefw.model.sys.ProductPropertyTemp;
import com.jeefw.service.sys.ProductPropertyTempService;

import core.service.BaseService;

/**
 *  商品属性规格模板的业务逻辑层的实现
 * @ 
 */
@Service
public class ProductPropertyTempServiceImpl extends BaseService<ProductPropertyTemp> implements ProductPropertyTempService {

	private ProductPropertyTempDao productPropertyTempDao;

	@Resource
	public void ProductPropertyTempDao(ProductPropertyTempDao productPropertyTempDao) {
		this.productPropertyTempDao = productPropertyTempDao;
		this.dao = productPropertyTempDao;
	}

	public List<ProductPropertyTemp> queryProductPropertyTempWithSubList(List<ProductPropertyTemp> resultList) {
		List<ProductPropertyTemp> dictList = new ArrayList<ProductPropertyTemp>();
		for (ProductPropertyTemp entity : resultList) {
			ProductPropertyTemp dict = new ProductPropertyTemp();
			dict.setId(entity.getId());
			dict.setPropertyKey(entity.getPropertyKey());
			dict.setPropertyValue(entity.getPropertyValue());
			dict.setSequence(entity.getSequence());
			dict.setParentPropertykey(entity.getParentPropertykey());
			dict.setParentPropertyValue(entity.getParentPropertyValue());
			dictList.add(dict);
		}
		return dictList;
	}

}
