package com.jeefw.dao.sys.impl;

import com.jeefw.model.sys.ProductInfo;
import core.support.QueryResult;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ProductShelfDao;
import com.jeefw.model.sys.ProductShelf;

import core.dao.BaseDao;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

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

	@Override
	public QueryResult selectProductShelfByParam(ProductShelf productShelf) {
		StringBuffer hql = new StringBuffer("from ProductShelf where 1=1 ");
		if (!StringUtils.isEmpty(productShelf.get$eq_allianceId())) {
			hql.append(" and allianceId = " + productShelf.get$eq_allianceId());
		}
		if (!StringUtils.isEmpty(productShelf.get$eq_bigCategoryNo())) {
			hql.append(" and bigCategoryNo = '" + productShelf.get$eq_bigCategoryNo() + "'");
		}
		if (!StringUtils.isEmpty(productShelf.get$eq_smallCategoryNo())) {
			hql.append(" and smallCategoryNo = '" + productShelf.get$eq_smallCategoryNo() + "'");
		}
		if (!StringUtils.isEmpty(productShelf.get$eq_minPrice()) && StringUtils.isEmpty(productShelf.get$eq_maxPrice())) {
			hql.append(" and salePrice < " + productShelf.get$eq_minPrice());
		}
		if (!StringUtils.isEmpty(productShelf.get$eq_maxPrice()) && StringUtils.isEmpty(productShelf.get$eq_minPrice())) {
			hql.append(" and salePrice > " + productShelf.get$eq_maxPrice());
		}
		if (!StringUtils.isEmpty(productShelf.get$eq_minPrice()) && !StringUtils.isEmpty(productShelf.get$eq_maxPrice())) {
			hql.append(" and salePrice between " + productShelf.get$eq_minPrice() + " and " + productShelf.get$eq_maxPrice());
		}
		if (!StringUtils.isEmpty(productShelf.getSortedConditions())) {
			hql.append(" order by ");
			Map<String, String> sortMap = productShelf.getSortedConditions();
			for (Map.Entry<String, String> entry : sortMap.entrySet()) {
				hql.append(entry.getKey() + " " + entry.getValue() + ",");
			}
			hql = new StringBuffer(hql.substring(0, hql.length() - 1));
		}

		System.out.println(hql.toString());
		QueryResult queryResult = new QueryResult();
		Query count = this.getSession().createQuery(hql.toString());
		queryResult.setTotalCount(Long.valueOf(count.list().size()));

		Query query = this.getSession().createQuery(hql.toString());
		if (!StringUtils.isEmpty(productShelf.getFirstResult())) {
			query.setFirstResult(productShelf.getFirstResult());
		}
		if (!StringUtils.isEmpty(productShelf.getMaxResults())) {
			query.setMaxResults(productShelf.getMaxResults());
		}
		queryResult.setResultList(query.list());
		return queryResult;
	}

	@Override
	public List<ProductShelf> queryProductShelfListByIdIn(List<Long> productShelfIds) {
		String str = "";
		if (productShelfIds.size() > 0) {
			StringBuffer ids = new StringBuffer();
			for (int i = 0; i < productShelfIds.size(); i++) {
				ids.append(productShelfIds.get(i) + ",");
			}
			str = ids.substring(0, ids.length() - 1);
		}

		String hql = "from ProductShelf where id in (" + str + ")";
		System.out.println(hql);
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}


}
