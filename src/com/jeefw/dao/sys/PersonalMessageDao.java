package com.jeefw.dao.sys;

import java.util.List;

import com.jeefw.model.sys.PersonalMessage;

import core.dao.Dao;

/**
 * 个人消息的数据持久层的接口
 * @ 
 */
public interface PersonalMessageDao extends Dao<PersonalMessage> {

	List <Object[]> queryValidCredit(String hql);



}
