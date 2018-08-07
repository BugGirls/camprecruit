package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 


import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzIndexShuffingDao; 
import com.jeefw.model.sys.TzzIndexShuffing; 
import com.jeefw.service.sys.TzzDictionaryService;
import com.jeefw.service.sys.TzzIndexShuffingService;

import core.service.BaseService;

/**
 * 活动轮播的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzIndexShuffingServiceImpl extends BaseService<TzzIndexShuffing> implements TzzIndexShuffingService {

	private TzzIndexShuffingDao tzzIndexShuffingDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	public void setTzzIndexShuffingDao(TzzIndexShuffingDao tzzIndexShuffingDao) {
		this.tzzIndexShuffingDao = tzzIndexShuffingDao;
		this.dao = tzzIndexShuffingDao;
	}

	public List<TzzIndexShuffing> queryTzzIndexShuffingWithSubList(List<TzzIndexShuffing> resultList) {
		List<TzzIndexShuffing> tzzIndexShuffingList = new ArrayList<TzzIndexShuffing>();
		for (TzzIndexShuffing entity : resultList) {
			TzzIndexShuffing tzzIndexShuffing = new TzzIndexShuffing();
			tzzIndexShuffing.setId(entity.getId());
			tzzIndexShuffing.setImage(entity.getImage());
			tzzIndexShuffing.setTitle(entity.getTitle());
			tzzIndexShuffing.setHref(entity.getHref());
			tzzIndexShuffing.setSort(entity.getSort());
			tzzIndexShuffing.setState(entity.getState());
			tzzIndexShuffingList.add(tzzIndexShuffing);
		}
		return tzzIndexShuffingList;
	}

	@Override
	public List<TzzIndexShuffing> queryTzzIndexShuffingCnList(List<TzzIndexShuffing> resultList) {
		 List<TzzIndexShuffing> list=new ArrayList<TzzIndexShuffing>();
		 for (TzzIndexShuffing entity : resultList) {
				TzzIndexShuffing tzzIndexShuffing = new TzzIndexShuffing();
				tzzIndexShuffing.setId(entity.getId());
				tzzIndexShuffing.setImage(entity.getImage());
				tzzIndexShuffing.setTitle(entity.getTitle());
				tzzIndexShuffing.setHref(entity.getHref());
				tzzIndexShuffing.setCreateTime(entity.getCreateTime());
				tzzIndexShuffing.setSort(entity.getSort());//排序
				if(!entity.getState().equals(null)){
					
				}   
				//轮播图类型；’0’=默认首页； 其他如学校客户首页； 营地客户首页； 社会客户首页 ; 网页悬浮窗
				list.add(tzzIndexShuffing);
			}
		return list;
	}
 

}
