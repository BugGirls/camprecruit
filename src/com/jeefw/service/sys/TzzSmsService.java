package com.jeefw.service.sys;

import java.util.List;
  
import com.jeefw.model.sys.TzzSms;

import core.service.Service;

/**
 *  
 * @ 
 */
public interface TzzSmsService extends Service<TzzSms> {

	List<TzzSms> queryTzzSmsWithSubList(List<TzzSms> resultList);
}
