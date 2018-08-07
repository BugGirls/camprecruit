package com.jeefw.service.sys;
 
 

 
 
import java.util.List;

import com.jeefw.model.sys.CompanyShenhe;

import core.service.Service;

/**
 * 用户的业务逻辑层的接口
 * @ 
 */
public interface CompanyShenheService extends Service<CompanyShenhe> {
 
	
	List<CompanyShenhe> getUserByids(List <Integer>ids);
	
}
