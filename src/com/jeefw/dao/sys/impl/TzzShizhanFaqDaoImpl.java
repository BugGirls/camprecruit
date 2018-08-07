package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzShizhanFaqDao;
import com.jeefw.model.sys.TzzShizhanFaq;

import core.dao.BaseDao;


   /**
    * TzzShizhanFaq的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzShizhanFaqDaoImpl extends  BaseDao<TzzShizhanFaq> implements TzzShizhanFaqDao {

	public TzzShizhanFaqDaoImpl() {
		super(TzzShizhanFaq.class);
	}

}

