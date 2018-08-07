package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.IntoWarehouseRecord;

import core.service.Service;

/**
 * 入库单的业务逻辑层的接口
 * @ 
 */
public interface IntoWarehouseRecordService extends Service<IntoWarehouseRecord> {

	List<IntoWarehouseRecord> queryIntoWarehouseRecordWithSubList(List<IntoWarehouseRecord> resultList);
 
}
