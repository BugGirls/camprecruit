package com.jeefw.dao.sys.impl;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 




 

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzNewsDao;
import com.jeefw.model.sys.TzzNews;

import core.dao.BaseDao;


   /**
    * TzzNews的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzNewsDaoImpl extends  BaseDao<TzzNews> implements TzzNewsDao {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public TzzNewsDaoImpl() {
		super(TzzNews.class);
	}

	/**
	 * 分页查询
	 * 查询的页码，和每页的条数
	 */
	@Override
	public List<TzzNews> getpage(int pagenum, int maxtiaoshu) { 
	 
		Query query =  this.getSession().createQuery("from TzzNews ORDER by id desc");
		query.setFirstResult(pagenum);
		query.setMaxResults(maxtiaoshu);
		return query.list();
	}
	
	/**
	 * 分页查询我的消息
	 * 查询的页码，和每页的条数
	 */
	@Override
	public List<TzzNews> getmynews(int pagenum, int maxtiaoshu ,int userId , Date time) { 
		pagenum = (pagenum-1)*maxtiaoshu;
		Query query =  this.getSession().createQuery("from TzzNews where (userId = 0 or userId=? ) and createTime > '"+sdf.format(time)+"' ORDER by id desc");
		query.setInteger(0, userId);
		query.setFirstResult(pagenum);
		query.setMaxResults(maxtiaoshu);
		return query.list();
	}

	/**
	 * 分页查询我的消息总条数
	 * 查询的页码，和每页的条数
	 */
	@Override
	public int getmynewsnum(int userId,Date time) { 
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(userId);
		Query query = this.getSession().createQuery("from TzzNews where userId in (:ids)  and createTime > '"+sdf.format(time)+"'");
		query = query.setParameterList("ids",ids ); 
		return query.list().size();
	}
	
	/**
	 * 未读条数
	 */
	@Override
	public int getnewsWDnum(int userId ,Date time) {
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(0);
		ids.add(userId);
		Query query = this.getSession().createQuery("from TzzNews where userId in (:ids) and state=0 and createTime > '"+sdf.format(time)+"'");
		query = query.setParameterList("ids",ids ); 
		return query.list().size();
	}
 	 
}

