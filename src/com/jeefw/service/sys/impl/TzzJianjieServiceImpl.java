package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 




import com.jeefw.dao.sys.TzzJianjieDao;
import com.jeefw.model.sys.TzzJianjie;
import com.jeefw.service.sys.TzzJianjieService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 信息发布的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzJianjieServiceImpl extends BaseService<TzzJianjie> implements TzzJianjieService {

	private TzzJianjieDao tzzJianjieDao;

	@Resource
	public void setTzzJianjieDao(TzzJianjieDao tzzJianjieDao) {
		this.tzzJianjieDao = tzzJianjieDao;
		this.dao = tzzJianjieDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<TzzJianjie> queryTzzJianjieHTMLList(List<TzzJianjie> resultList) {
		List<TzzJianjie> informationList = new ArrayList<TzzJianjie>();
		for (TzzJianjie entity : resultList) {
			TzzJianjie information = new TzzJianjie();
			information.setId(entity.getId());
			information.setImage(entity.getImage());  
			information.setRefreshTime(entity.getRefreshTime()); 
			information.setContent(entity.getContent());
			information.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			informationList.add(information);
		}
		return informationList;
	}
 

	// 全文检索信息

	public List<TzzJianjie> queryByTzzJianjieName(String name) {
		return tzzJianjieDao.queryByTzzJianjieName(name);
	}

	@Override
	public List<TzzJianjie> getindexlistTzzJianjies() { 
		return tzzJianjieDao.getindexList();
		 
	}
	// 生成信息的索引
	@Override
	public void indexingtzzJianjie() { 
		
	}

}
