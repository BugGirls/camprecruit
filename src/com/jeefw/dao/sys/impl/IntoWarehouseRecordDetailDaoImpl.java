package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.IntoWarehouseRecordDetailDao;
import com.jeefw.model.sys.IntoWarehouseRecordDatail;

import core.dao.BaseDao;

/**
 * 入库单详情的数据持久层的实现类
 * @ 
 */
@Repository
public class IntoWarehouseRecordDetailDaoImpl extends BaseDao<IntoWarehouseRecordDatail> implements IntoWarehouseRecordDetailDao {

	public IntoWarehouseRecordDetailDaoImpl() {
		super(IntoWarehouseRecordDatail.class);
	}

}
