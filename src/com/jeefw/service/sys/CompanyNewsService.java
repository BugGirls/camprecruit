package com.jeefw.service.sys;
 
 

 
 
import java.util.List;

import com.jeefw.model.sys.CompanyNews;

import core.service.Service;

/**
 * 用户的业务逻辑层的接口
 * @ 
 */
public interface CompanyNewsService extends Service<CompanyNews> {
 
	
	List<CompanyNews> getUserByids(List <Integer>ids);
	
}
