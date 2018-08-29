package com.jeefw.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductWarehouseDao;
import com.jeefw.model.sys.ProductWarehouse;

import core.dao.BaseDao;

/**
 * 库存商品的数据持久层的实现类
 * @ 
 */
@Repository
public class ProductWarehouseDaoImpl extends BaseDao<ProductWarehouse> implements ProductWarehouseDao {

	public ProductWarehouseDaoImpl() {
		super(ProductWarehouse.class);
	}

	@Override
	public Integer doCountProductWarehouseCount(Integer allianceId) {
		String hql = "select count(productNo) from ProductWarehouse where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Integer doCountProductTotalNum(Integer allianceId) {
		String hql = "select SUM(num) from ProductWarehouse where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Integer.parseInt((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountAdviceTotalAmount(Integer allianceId) {
		String hql = "select SUM(advicePrice) from ProductWarehouse where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public Float doCountSaleTotalAmount(Integer allianceId) {
		String hql = "select SUM(salePrice) from ProductWarehouse where allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, allianceId+"");
		return Float.parseFloat((null != query.uniqueResult())?query.uniqueResult().toString():"0");
	}

	@Override
	public void updateProductWarehouseNum(String productWarehouseId, Long num) {
		String hql = "update ProductWarehouse set num = ? where id = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, num);
		query.setParameter(1, productWarehouseId);
		query.executeUpdate();
	}

	@Override
	public List<ProductWarehouse> findByNoAndAllianceId(String productNo, String allianceId) {
		String hql = "from ProductWarehouse where productNo = ? and allianceId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, productNo);
		query.setParameter(1, allianceId);
		return query.list();
	}

}
