package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
  



import com.jeefw.dao.sys.TzzJianjieDao;
import com.jeefw.dao.sys.TzzMessageDao;
import com.jeefw.model.sys.TzzJianjie;
import com.jeefw.model.sys.TzzMessage;
import com.jeefw.service.sys.TzzJianjieService;
import com.jeefw.service.sys.TzzMessageService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 信息发布的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzMessageServiceImpl extends BaseService<TzzMessage> implements TzzMessageService {

	private TzzMessageDao tzzMessageDao;

	@Resource
	public void setTzzMessageDao(TzzMessageDao tzzMessageDao) {
		this.tzzMessageDao = tzzMessageDao;
		this.dao = tzzMessageDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<TzzMessage> queryTzzMessageHTMLList(List<TzzMessage> resultList) {
		List<TzzMessage> informationList = new ArrayList<TzzMessage>();
		for (TzzMessage entity : resultList) {
			TzzMessage information = new TzzMessage();
			information.setId(entity.getId());
			information.setType(entity.getType());//1=网站服务；2=帮助中心 ；3=交流合作
			if(entity.getType()==1){
				information.setTypecn("网站服务"); 
			}else if(entity.getType()==2){
				information.setTypecn("帮助中心"); 
			}else if(entity.getType()==3){
				information.setTypecn("交流合作"); 
			}
			information.setTitle(entity.getTitle());
			information.setCreateTime(entity.getCreateTime());
			information.setContent(entity.getContent());
			information.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			informationList.add(information);
		}
		return informationList;
	}
  

}
