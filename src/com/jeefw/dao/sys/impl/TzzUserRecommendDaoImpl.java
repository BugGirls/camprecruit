package com.jeefw.dao.sys.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserRecommendDao;
import com.jeefw.model.sys.TzzUserRecommend;
import com.jeefw.model.sys.param.TzzUserRecommendParameter;

import core.dao.BaseDao;


   /**
    * TzzUserRecommend的数据持久层的实现类 
    *
    */ 
@Repository
public class TzzUserRecommendDaoImpl extends  BaseDao<TzzUserRecommend> implements TzzUserRecommendDao {

	public TzzUserRecommendDaoImpl() {
		super(TzzUserRecommend.class);
	}

	@Override
	public List<TzzUserRecommendParameter> getTopRecommends() {
		String sql = "SELECT r.id,r.user_id,r.promotion_id,r.create_time,u.nickname as userName,p.`name` as promotionName FROM tzz_user_recommend r LEFT JOIN tzz_weixin_user u on r.user_id=u.id LEFT JOIN tzz_sales_promotion p on r.promotion_id=p.id ORDER BY r.create_time DESC LIMIT 0,10";
		Query query = this.getSession().createSQLQuery(sql); 
		List <Object[]> list = query.list();
		List <TzzUserRecommendParameter> recommends = new ArrayList<>();
		for (Object[] obj : list) {
//			s.id,s.name,s.brief,s.img,s.price,s.participation,
//			String name, String brief,  String img, BigDecimal price, Long participation,Date begin_date,Date end_date,int min_age,int max_age,String reservitionCount
			TzzUserRecommendParameter recommend = new TzzUserRecommendParameter((Integer)obj[0],(Integer)obj[1],(Integer)obj[2],(Date)obj[3],(String)obj[4],(String)obj[5]);
			recommends.add(recommend);
		}
		return recommends; 
	}

}

