package com.jeefw.dao.sys.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductOffShelfDao;
import com.jeefw.model.sys.ProductOffShelf;

import core.dao.BaseDao;

/**
 * 货架商品信息的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductOffShelfDaoImpl extends BaseDao<ProductOffShelf> implements ProductOffShelfDao {

	public ProductOffShelfDaoImpl() {
		super(ProductOffShelf.class);
	}

	@Override
	public Integer doCountProductWarehouseCount(Integer allianceId) {
		String hql = "select count(productNo) from ProductOffShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Integer doCountProductTotalNum(Integer allianceId) {
		String hql = "select SUM(num) from ProductOffShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountAdviceTotalAmount(Integer allianceId) {
		String hql = "select SUM(intoPrice) from ProductOffShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountSaleTotalAmount(Integer allianceId) {
		String hql = "select SUM(salePrice) from ProductOffShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

}
