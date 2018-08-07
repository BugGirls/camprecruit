package com.jeefw.service.sys.impl;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
   
 



import com.jeefw.dao.sys.TzzNewsDao; 
import com.jeefw.model.sys.TzzNews;
import com.jeefw.service.sys.TzzNewsService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzNewsServiceImpl extends BaseService<TzzNews> implements TzzNewsService {

	private TzzNewsDao tzzNewsDao; 
	@Resource
	public void setTzzNewsDao(TzzNewsDao tzzNewsDao) {
		this.tzzNewsDao = tzzNewsDao;
		this.dao = tzzNewsDao;
	}

	public List<TzzNews> queryTzzNewsWithSubList(List<TzzNews> resultList) {
		List<TzzNews> tzzNewsList = new ArrayList<TzzNews>();
		for (TzzNews entity : resultList) {
			TzzNews tzzNews = new TzzNews();
			tzzNews.setId(entity.getId());
			tzzNews.setUserId(entity.getUserId());
			tzzNews.setTitle(entity.getTitle());
			tzzNews.setContent(entity.getContent());
			tzzNews.setCreateTime(entity.getCreateTime()); 
			tzzNewsList.add(tzzNews);
		}
		return tzzNewsList;
	}

	@Override
	public List<TzzNews> getnewspage(int pagenum, int maxtiaoshu) {
		pagenum=(pagenum-1)*maxtiaoshu;
		return tzzNewsDao.getpage(pagenum, maxtiaoshu);
	 
	}

	@Override
	public List<TzzNews> queryNewsSubContent(List<TzzNews> resultList) {
		List<TzzNews> tzzNewsList = new ArrayList<TzzNews>();
		for (TzzNews entity : resultList) {
			TzzNews tzzNews = new TzzNews();
			tzzNews.setId(entity.getId());
			tzzNews.setUserId(entity.getUserId());
			tzzNews.setTitle(entity.getTitle());
			String content = entity.getContent();
			if (content.length() > 120){
				content = content.substring(0, 120);
				content = content + "……";
			}else if(content.length() < 120){
				content = content + "<br/>";
			}
			tzzNews.setContent(content);
			tzzNews.setCreateTime(entity.getCreateTime()); 
			tzzNewsList.add(tzzNews);
		}
		return tzzNewsList;
	}

	@Override
	public int getnewsWDnum(int userId , Date time) {
		return tzzNewsDao.getnewsWDnum(userId ,time);
	}

	@Override
	public List<TzzNews> getmynews(int pagenum, int maxtiaoshu, int userId , Date time) {
		 return tzzNewsDao.getmynews(pagenum, maxtiaoshu, userId , time);
	}

	@Override
	public int getmynewsnum(int userId, Date time ) {
		 
		return tzzNewsDao.getmynewsnum(userId,  time);
	}
  
}
