package com.jeefw.service.sys;
 
import java.util.List;

import com.jeefw.model.sys.TzzDictionary;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface TzzDictionaryService extends Service<TzzDictionary> {

	void deleteTzzDicById(int tdId);
 
	List<TzzDictionary> queryTzzDictCnList(List<TzzDictionary> resultList);
	
	
	List<TzzDictionary> getDictionaryByids(List <Integer>ids);
	 
 
}
