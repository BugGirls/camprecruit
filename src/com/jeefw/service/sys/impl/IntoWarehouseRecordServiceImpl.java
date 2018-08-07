package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.IntoWarehouseRecordDao;
import com.jeefw.model.sys.IntoWarehouseRecord;
import com.jeefw.service.sys.IntoWarehouseRecordService;

import core.service.BaseService;

/**
 * 入库单的业务逻辑层的实现
 * @ 
 */
@Service
public class IntoWarehouseRecordServiceImpl extends BaseService<IntoWarehouseRecord> implements IntoWarehouseRecordService {

	private IntoWarehouseRecordDao intoWarehouseRecordDao;

	@Resource
	public void setIntoWarehouseRecordDao(IntoWarehouseRecordDao intoWarehouseRecordDao) {
		this.intoWarehouseRecordDao = intoWarehouseRecordDao;
		this.dao = intoWarehouseRecordDao;
	}

	public List<IntoWarehouseRecord> queryIntoWarehouseRecordWithSubList(List<IntoWarehouseRecord> resultList) {
		List<IntoWarehouseRecord> dictList = new ArrayList<IntoWarehouseRecord>();
		for (IntoWarehouseRecord entity : resultList) {
			IntoWarehouseRecord intoWarehouseRecord = new IntoWarehouseRecord();
			intoWarehouseRecord.setId(entity.getId());
			intoWarehouseRecord.setNo(entity.getNo());
			intoWarehouseRecord.setCreater(entity.getCreater());
			intoWarehouseRecord.setCreatetime(entity.getCreatetime());
			intoWarehouseRecord.setTitle(entity.getTitle());
			intoWarehouseRecord.setIntoTotalAmount(entity.getIntoTotalAmount());
			intoWarehouseRecord.setSaleTotalAmount(entity.getSaleTotalAmount());
			intoWarehouseRecord.setIntoNum(entity.getIntoNum());
			intoWarehouseRecord.setIntoTypeNum(entity.getIntoTypeNum());
			intoWarehouseRecord.setContent(entity.getContent());
			intoWarehouseRecord.setSupplier(entity.getSupplier());
			intoWarehouseRecord.setAllianceId(entity.getAllianceId());
			dictList.add(intoWarehouseRecord);
		}
		return dictList;
	}

}
