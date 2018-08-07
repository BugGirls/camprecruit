package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.Dict;
import com.jeefw.model.sys.Emailphone; 

import core.service.Service;

/**
 *  
 * @ 
 */
public interface EmailphoneService extends Service<Emailphone> {

	List<Emailphone> queryEmailphoneWithSubList(List<Emailphone> resultList);
}
