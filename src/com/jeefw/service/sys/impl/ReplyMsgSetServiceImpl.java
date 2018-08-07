package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.ReplyMsgSetDao;
import com.jeefw.model.sys.ReplyMsgSet;
import com.jeefw.service.sys.ReplyMsgSetService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class ReplyMsgSetServiceImpl extends BaseService<ReplyMsgSet> implements ReplyMsgSetService {

	private ReplyMsgSetDao replyMsgSetDao;

	@Resource
	public void setRoleDao(ReplyMsgSetDao replymsgDao) {
		this.replyMsgSetDao = replymsgDao;
		this.dao = replymsgDao;
	}

}
