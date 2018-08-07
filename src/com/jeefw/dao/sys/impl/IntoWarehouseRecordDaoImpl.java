package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.IntoWarehouseRecordDao;
import com.jeefw.model.sys.IntoWarehouseRecord;

import core.dao.BaseDao;

/**
 * 入库单的数据持久层的实现类
 * @ 
 */
@Repository
public class IntoWarehouseRecordDaoImpl extends BaseDao<IntoWarehouseRecord> implements IntoWarehouseRecordDao {

	public IntoWarehouseRecordDaoImpl() {
		super(IntoWarehouseRecord.class);
	}

}
