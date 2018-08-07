package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.DeliveryRecordDao;
import com.jeefw.model.sys.DeliveryRecord;

import core.dao.BaseDao;

/**
 * 投递简历的数据持久层的实现类
 * @ 
 */
@Repository
public class DeliveryRecordDaoImpl extends BaseDao<DeliveryRecord> implements DeliveryRecordDao {

	public DeliveryRecordDaoImpl() {
		super(DeliveryRecord.class);
	}

}
