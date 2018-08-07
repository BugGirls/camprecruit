package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.IntoWarehouseRecordDatail;

import core.service.Service;

/**
 * 入库单详情的业务逻辑层的接口
 * @ 
 */
public interface IntoWarehouseRecordDetailService extends Service<IntoWarehouseRecordDatail> {

	List<IntoWarehouseRecordDatail> queryIntoWarehouseRecordDatailWithSubList(List<IntoWarehouseRecordDatail> resultList);
 
}