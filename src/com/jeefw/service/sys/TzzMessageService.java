package com.jeefw.service.sys;

import java.util.List;
 
 
import com.jeefw.model.sys.TzzMessage;

import core.service.Service;

/**
 * 信息发布的业务逻辑层的接口
 * @ 
 */
public interface TzzMessageService extends Service<TzzMessage> {

	// 获取信息，包括内容的HTML和过滤HTML两部分
	List<TzzMessage> queryTzzMessageHTMLList(List<TzzMessage> resultList);

 
}
