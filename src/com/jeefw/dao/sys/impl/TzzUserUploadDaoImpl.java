package com.jeefw.dao.sys.impl;



import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.TzzUserUploadDao;
import com.jeefw.model.sys.TzzUserUpload;

import core.dao.BaseDao;


   /**
    * TzzUserUpload的数据持久层的实现类 
    * 2015/09/18 10:46:31  tudou
    */ 
@Repository
public class TzzUserUploadDaoImpl extends  BaseDao<TzzUserUpload> implements TzzUserUploadDao {

	public TzzUserUploadDaoImpl() {
		super(TzzUserUpload.class);
	}

}

