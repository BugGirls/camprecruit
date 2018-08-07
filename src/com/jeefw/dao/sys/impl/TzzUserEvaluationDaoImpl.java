package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserEvaluationDao;
import com.jeefw.model.sys.TzzUserEvaluation;

import core.dao.BaseDao;


   /**
    * TzzUserEvaluation的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserEvaluationDaoImpl extends  BaseDao<TzzUserEvaluation> implements TzzUserEvaluationDao {

	public TzzUserEvaluationDaoImpl() {
		super(TzzUserEvaluation.class);
	}
	
	



	@Override
	public int getUserEvaluationbyProductId(int productId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from tzz_user_evaluation where 1 = 1 ";
		if (productId > 0 ){
			sql += " and product_id = " + productId;
		}
		String countAll = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		int count = Integer.valueOf(countAll);
		return count;
	}

}

