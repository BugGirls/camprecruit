package com.jeefw.service.sys;

import java.util.List;
 

import com.jeefw.model.sys.TzzQuestion;

import core.service.Service;

/**
 * 信息发布的业务逻辑层的接口
 * @ 
 */
public interface TzzQuestionService extends Service<TzzQuestion> {

	// 获取信息，包括内容的HTML和过滤HTML两部分
	List<TzzQuestion> queryTzzQuestionHTMLList(List<TzzQuestion> resultList);

	//问答详情
	TzzQuestion getQuestion(int id);
	// 生成信息的索引
	void indexingTzzQuestion();
	//问答页面首页列表
	List<TzzQuestion> getstaticQuestionslist(int num);
	//热门问答
	List<TzzQuestion> gethotQuestionslist(int num);
	//分类搜索
	List<TzzQuestion>searchQuestionslist(String family,String order, int pagenum,int size);
	//分类条数
	int searchQuestionspagenum(String family,String order, int size);
	
	
	// 全文检索信息
	List<TzzQuestion> queryByTzzQuestionName(String name);

}
