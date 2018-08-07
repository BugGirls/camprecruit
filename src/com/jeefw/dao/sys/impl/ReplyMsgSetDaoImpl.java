package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.ReplyMsgSetDao;
import com.jeefw.model.sys.ReplyMsgSet;

import core.dao.BaseDao;

/**
 * 消息回复设置的数据持久层的实现类
 * @ 
 */
@Repository
public class ReplyMsgSetDaoImpl extends BaseDao<ReplyMsgSet> implements ReplyMsgSetDao {

	public ReplyMsgSetDaoImpl() {
		super(ReplyMsgSet.class);
	}

}
