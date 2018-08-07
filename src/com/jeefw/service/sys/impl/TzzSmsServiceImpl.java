package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
     
import com.jeefw.dao.sys.TzzSmsDao;
import com.jeefw.model.sys.TzzSms;
import com.jeefw.service.sys.TzzSmsService; 

import core.service.BaseService;

/**
 * 邮件的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzSmsServiceImpl  extends BaseService<TzzSms> implements TzzSmsService {

	private TzzSmsDao tzzSmsDao;

	@Resource
	public void setTzzSmsDao(TzzSmsDao tzzSmsDao) {
		this.tzzSmsDao = tzzSmsDao;
		this.dao = tzzSmsDao;
	}
	
	public List<TzzSms> queryTzzSmsWithSubList(List<TzzSms> resultList) {
		List<TzzSms> tzzSmsList = new ArrayList<TzzSms>();
		for (TzzSms entity : resultList) {
			TzzSms ep = new TzzSms();
			ep.setId(entity.getId());
			ep.setContent(entity.getContent());
			ep.setPhone(entity.getPhone()); 
			ep.setCreateTime(entity.getCreateTime());
			tzzSmsList.add(ep);
		}
		return tzzSmsList;
	}
 

}
