package com.jeefw.service.sys;

import java.util.List;
 

import com.jeefw.model.sys.TzzAnswer; 

import core.service.Service;

/**
 * 信息发布的业务逻辑层的接口
 * @ 
 */
public interface TzzAnswerService extends Service<TzzAnswer> {

	// 获取信息，包括内容的HTML和过滤HTML两部分
	List<TzzAnswer> queryTzzAnswerHTMLList(List<TzzAnswer> resultList);

	// 生成信息的索引
	void indexingTzzAnswer();
	//分页 
	List<TzzAnswer> getTzzAnswerlist(List<TzzAnswer> resultList ,int pagenum,int pagesize);
	// 全文检索信息
	List<TzzAnswer> queryByTzzAnswerName(String name);

}
