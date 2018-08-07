package com.jeefw.service.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.DeliveryRecordDao;
import com.jeefw.model.sys.DeliveryRecord;
import com.jeefw.service.sys.DeliveryRecordService;

import core.service.BaseService;

/**
 * 简历投递记录的业务逻辑层的实现
 * @ 
 */
@Service
public class DeliveryRecordServiceImpl extends BaseService<DeliveryRecord> implements DeliveryRecordService {

	private DeliveryRecordDao deliveryRecordDao;

	@Resource
	public void setDeliveryRecordDao(DeliveryRecordDao deliveryRecordDao) {
		this.deliveryRecordDao = deliveryRecordDao;
		this.dao = deliveryRecordDao;
	}

}
