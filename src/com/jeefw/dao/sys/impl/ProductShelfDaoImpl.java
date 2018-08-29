package com.jeefw.dao.sys.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductShelfDao;
import com.jeefw.model.sys.ProductShelf;

import core.dao.BaseDao;

/**
 * 货架商品信息的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductShelfDaoImpl extends BaseDao<ProductShelf> implements ProductShelfDao {

	public ProductShelfDaoImpl() {
		super(ProductShelf.class);
	}

	@Override
	public Integer doCountProductWarehouseCount(Integer allianceId) {
		String hql = "select count(productNo) from ProductShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Integer doCountProductTotalNum(Integer allianceId) {
		String hql = "select SUM(num) from ProductShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountAdviceTotalAmount(Integer allianceId) {
		String hql = "select SUM(intoPrice) from ProductShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountSaleTotalAmount(Integer allianceId) {
		String hql = "select SUM(salePrice) from ProductShelf where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public void updateProductOnShelfNum(String onShelfId, Long num) {
		String hql = "update ProductShelf set num = ? where id = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, num);
		query.setParameter(1, onShelfId);
		query.executeUpdate();
	}

}
