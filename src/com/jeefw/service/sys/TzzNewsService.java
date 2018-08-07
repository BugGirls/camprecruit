package com.jeefw.service.sys;

import java.util.Date;
import java.util.List;
 



import com.jeefw.model.sys.TzzNews; 

import core.service.Service;

/**
 * 字典的业务逻辑层的接口
 * @ 
 */
public interface TzzNewsService extends Service<TzzNews> {

	List<TzzNews> queryTzzNewsWithSubList(List<TzzNews> resultList);
	
	List<TzzNews> queryNewsSubContent(List<TzzNews> resultList);

	List<TzzNews> getnewspage( int pagenum, int maxtiaoshu);
	
	/**
	 * 我的未读消息条数
	 * @param userId
	 * @return
	 */
	int getnewsWDnum(int userId ,Date time);
	/**
	 * 我的消息
	 * @param pagenum
	 * @param maxtiaoshu
	 * @return
	 */
	List<TzzNews> getmynews(int pagenum, int maxtiaoshu , int userId,Date time );
	/**
	 * 我的消息条数
	 * @param userId
	 * @return
	 */
	int getmynewsnum(int userId,Date time);
 
}
