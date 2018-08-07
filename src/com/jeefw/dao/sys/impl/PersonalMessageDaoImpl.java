package com.jeefw.dao.sys.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.PersonalMessageDao;
import com.jeefw.model.sys.PersonalMessage;

import core.dao.BaseDao;

/**
 * 个人消息的数据持久层的实现类
 * @ 
 */
@Repository
public class PersonalMessageDaoImpl extends BaseDao<PersonalMessage> implements PersonalMessageDao {

	public PersonalMessageDaoImpl() {
		super(PersonalMessage.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List <Object[]> queryValidCredit(String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}


}
