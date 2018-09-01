package com.jeefw.dao.sys.impl;

import com.jeefw.model.sys.ProductShelf;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductInfoDao;
import com.jeefw.model.sys.ProductInfo;

import core.dao.BaseDao;

import java.util.List;

/**
 * 商品信息的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductInfoDaoImpl extends BaseDao<ProductInfo> implements ProductInfoDao {

	public ProductInfoDaoImpl() {
		super(ProductInfo.class);
	}

	@Override
	public List<ProductInfo> queryProductListByNoIn(List<String> productNoList) {
		String str = "";
		if (productNoList.size() > 0) {
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < productNoList.size(); i++) {
				ids.append("'" + productNoList.get(i) + "',");
			}
			str = ids.substring(0, ids.length() - 1);
		}

		String hql = "from ProductInfo where no in (" + str + ")";
		System.out.println(hql);
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

}
