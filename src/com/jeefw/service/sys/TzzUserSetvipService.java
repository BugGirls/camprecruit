package com.jeefw.service.sys;
  

import java.util.List;

import com.jeefw.model.sys.TzzUserSetvip;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface TzzUserSetvipService extends Service<TzzUserSetvip> {

	void deleteTzzusersetvipById(int tdId);
 
	List<TzzUserSetvip>  getbyMinamounDesc();
}
