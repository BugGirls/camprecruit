package com.jeefw.dao.sys;



import java.util.Date;
import java.util.List;
 

import com.jeefw.model.sys.TzzNews;

import core.dao.Dao;


   /**
    * TzzNews数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzNewsDao extends  Dao<TzzNews>{
	List<TzzNews> getpage(int pagenum, int maxtiaoshu);
	
	int getnewsWDnum(int userId , Date time);
	/**
	 * 我的消息
	 * @param pagenum
	 * @param maxtiaoshu
	 * @return
	 */
	List<TzzNews> getmynews(int pagenum, int maxtiaoshu , int userId , Date time);
	/**
	 * 我的消息条数
	 * @param userId
	 * @return
	 */
	int getmynewsnum(int userId , Date time);
}
