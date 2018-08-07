package com.jeefw.service.sys;
 
 

 
 
import java.util.List;

import com.jeefw.model.sys.CompanyUser;

import core.service.Service;

/**
 * 用户的业务逻辑层的接口
 * @ 
 */
public interface CompanyUserService extends Service<CompanyUser> {
 
	
	List<CompanyUser> getUserByids(List <Integer>ids);
	
}
