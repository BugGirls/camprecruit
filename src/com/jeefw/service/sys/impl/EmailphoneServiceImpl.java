package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.EmailphoneDao;  
import com.jeefw.model.sys.Emailphone; 
import com.jeefw.service.sys.EmailphoneService; 

import core.service.BaseService;

/**
 * 邮件的业务逻辑层的实现
 * @ 
 */
@Service
public class EmailphoneServiceImpl  extends BaseService<Emailphone> implements EmailphoneService {

	private EmailphoneDao emailphoneDao;

	@Resource
	public void setEmailphoneDao(EmailphoneDao emailphoneDao) {
		this.emailphoneDao = emailphoneDao;
		this.dao = emailphoneDao;
	}
	
	public List<Emailphone> queryEmailphoneWithSubList(List<Emailphone> resultList) {
		List<Emailphone> dictList = new ArrayList<Emailphone>();
		for (Emailphone entity : resultList) {
			Emailphone ep = new Emailphone();
			ep.setId(entity.getId());
			ep.setEmail(entity.getEmail());
			ep.setEstate(entity.getEstate());
			ep.setPhone(entity.getPhone());
			ep.setPstate(entity.getPstate());
			ep.setChannel(entity.getChannel());
			ep.setSmspwd(entity.getSmspwd());
			ep.setSmtp(entity.getSmtp());
			ep.setPwd(entity.getPwd());
			ep.setCreateTime(entity.getCreateTime());
			dictList.add(ep);
		}
		return dictList;
	}

}
