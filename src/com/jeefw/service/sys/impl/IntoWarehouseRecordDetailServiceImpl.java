package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.IntoWarehouseRecordDetailDao;
import com.jeefw.model.sys.IntoWarehouseRecordDatail;
import com.jeefw.service.sys.IntoWarehouseRecordDetailService;

import core.service.BaseService;

/**
 * 入库单详情的业务逻辑层的实现
 * @ 
 */
@Service
public class IntoWarehouseRecordDetailServiceImpl extends BaseService<IntoWarehouseRecordDatail> implements IntoWarehouseRecordDetailService {

	private IntoWarehouseRecordDetailDao intoWarehouseRecordDetailDao;

	@Resource
	public void setIntoWarehouseRecordDetailDao(IntoWarehouseRecordDetailDao intoWarehouseRecordDetailDao) {
		this.intoWarehouseRecordDetailDao = intoWarehouseRecordDetailDao;
		this.dao = intoWarehouseRecordDetailDao;
	}

	public List<IntoWarehouseRecordDatail> queryIntoWarehouseRecordDatailWithSubList(List<IntoWarehouseRecordDatail> resultList) {
		List<IntoWarehouseRecordDatail> dictList = new ArrayList<IntoWarehouseRecordDatail>();
		for (IntoWarehouseRecordDatail entity : resultList) {
			IntoWarehouseRecordDatail datail = new IntoWarehouseRecordDatail();
<<<<<<< HEAD
//			intoWarehouseRecord.setId(entity.getId());
//			intoWarehouseRecord.setNo(entity.getNo());
//			intoWarehouseRecord.setCreater(entity.getCreater());
//			intoWarehouseRecord.setCreatetime(entity.getCreatetime());
//			intoWarehouseRecord.setTitle(entity.getTitle());
//			intoWarehouseRecord.setIntoTotalAmount(entity.getIntoTotalAmount());
//			intoWarehouseRecord.setSaleTotalAmount(entity.getSaleTotalAmount());
//			intoWarehouseRecord.setIntoNum(entity.getIntoNum());
//			intoWarehouseRecord.setIntoTypeNum(entity.getIntoTypeNum());
//			intoWarehouseRecord.setContent(entity.getContent());
//			intoWarehouseRecord.setSupplier(entity.getSupplier());
//			intoWarehouseRecord.setAllianceId(entity.getAllianceId());
=======
			datail.setId(entity.getId());
			datail.setIntoWarehouseRecordNo(entity.getIntoWarehouseRecordNo());
			datail.setProductNo(entity.getProductNo());
			datail.setProductName(entity.getProductName());
			datail.setProductBarCode(entity.getProductBarCode());
			datail.setSeqence(entity.getSeqence());
			datail.setNum(entity.getNum());
			datail.setSalePrice(entity.getSalePrice());
			datail.setAdvicePrice(entity.getAdvicePrice());
			datail.setContent(entity.getContent());
			datail.setStorageLocation(entity.getStorageLocation());
			datail.setProductionDate(entity.getProductionDate());
			datail.setAllianceId(entity.getAllianceId());
>>>>>>> merge project
			dictList.add(datail);
		}
		return dictList;
	}

}
