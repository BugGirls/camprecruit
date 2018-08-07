package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.InformationDao;
import com.jeefw.model.sys.Information;
import com.jeefw.service.sys.InformationService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 信息发布的业务逻辑层的实现
 * @ 
 */
@Service
public class InformationServiceImpl extends BaseService<Information> implements InformationService {

	private InformationDao informationDao;

	@Resource
	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
		this.dao = informationDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<Information> queryInformationHTMLList(List<Information> resultList) {
		List<Information> informationList = new ArrayList<Information>();
		for (Information entity : resultList) {
			Information information = new Information();
			information.setId(entity.getId());
			information.setImage(entity.getImage()); 
			information.setTitle(entity.getTitle());
			information.setAuthor(entity.getAuthor());
			information.setRefreshTime(entity.getRefreshTime());
			information.setSort(entity.getSort());
			information.setVideo(entity.getVideo());
			information.setContent(entity.getContent());
			information.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			informationList.add(information);
		}
		return informationList;
	}
	public List<Information> queryTopInformationHTMLList(List<Information> resultList){
		List<Information> informationList = new ArrayList<Information>();
		for (Information entity : resultList) {
			Information information = new Information();
			information.setId(entity.getId());
			information.setImage(entity.getImage()); 
			information.setTitle(entity.getTitle());
			if(entity.getTitle().length()>25){
				entity.setSubtitle(entity.getTitle().substring(0,25)+"...");
			}else {
				entity.setSubtitle(entity.getTitle());
			}
			information.setAuthor(entity.getAuthor());
			information.setRefreshTime(entity.getRefreshTime());
			information.setSort(entity.getSort());
			information.setVideo(entity.getVideo());
			information.setContent(entity.getContent());
			 
			information.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			if(information.getContentNoHTML().length()>80){
				information.setContentNoHTML(information.getContentNoHTML().substring(0,80)+"...");
			}
			informationList.add(information);
		}
		return informationList;
	}
	// 生成信息的索引

	public void indexingInformation() {
		informationDao.indexingInformation();
	}

	// 全文检索信息

	public List<Information> queryByInformationName(String name) {
		return informationDao.queryByInformationName(name);
	}

	@Override
	public List<Information> getindexlistInformations() { 
		return informationDao.getindexList();
		 
	}

}
