package com.jeefw.service.sys;
 
 

 
 
import java.util.List;

import com.jeefw.model.sys.CompanyInfo;

import core.service.Service;

/**
 * 用户的业务逻辑层的接口
 * @ 
 */
public interface CompanyInfoService extends Service<CompanyInfo> {
 
	
	List<CompanyInfo> getUserByids(List <Integer>ids);
	
}
