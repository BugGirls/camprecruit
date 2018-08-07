package com.jeefw.dao.sys;



import java.util.List;
 


import com.jeefw.model.sys.TzzQuestion;

import core.dao.Dao;


   /**
    * TzzQuestion数据持久层的接口 
    * Fri Sep 18 09:59:33 CST 2015  tudou
    */ 
public interface TzzQuestionDao extends  Dao<TzzQuestion>{
	// 生成信息的索引
	void indexingTzzQuestion();

	// 全文检索信息
	List<TzzQuestion> queryByTzzQuestionName(String name);
	List<TzzQuestion> getstaticQuestionslist(String hql, int size);
	//按条件分页
	List<TzzQuestion> searchQuestionslist(String hql,List<Integer> family ,int page , int size );

	List<TzzQuestion> searchQuestionspagenum(String string, List<Integer> fs); 
	
}
