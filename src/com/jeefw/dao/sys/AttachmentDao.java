package com.jeefw.dao.sys;

import java.util.List;

import com.jeefw.model.sys.Attachment;

import core.dao.Dao;

/**
 * 附件的数据持久层的接口
 * @ 
 */
public interface AttachmentDao extends Dao<Attachment> {

	List<Object[]> queryFlowerList(String epcId);

	void deleteAttachmentByForestryTypeId(Long umTypeId);

	List<Object[]> querySensorList();

}
