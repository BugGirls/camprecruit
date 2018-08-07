package com.jeefw.service.sys;
 
import java.util.List;

import com.jeefw.model.sys.Information;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzSafetyNews;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface TzzSafetyNewsService extends Service<TzzSafetyNews> {

	void deleteTzzSafetyNewsById(int tdId);
	
	 List<TzzSafetyNews> queryTzzSafetyNewsCnList(List<TzzSafetyNews> resultList,List<TzzDictionary> dict);
	 List<TzzSafetyNews> queryHotByorder(String order);
	 List<TzzSafetyNews> getSafetyNewsListByIds(List<Integer> ids);
	 List<TzzSafetyNews> queryAllByorder(String order,int page);
	 int pageAllByorder(String order);
	
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	 List<TzzSafetyNews> searchByName(String name , int page , String order );
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	int searchByNamepages(String name);
	 
	 /**
	  * 分类查询
	  * @param family
	  * @param page
	  * @param order
	  * @return
	  */
	 List<TzzSafetyNews> searchSafetyNews(int  family,int page, String order);//
	 /**
	  * 分类查询总页数
	  * @param family
	  * @param order
	  * @return
	  */
	 int searchSafetyNewsPages(int  family, String order);
	 
	 List<TzzSafetyNews> searchSafetyNews(int pagenum, int type);

	List<TzzSafetyNews> queryTopInformationHTMLList(List<TzzSafetyNews> ilist);

	Integer searchSafetyNewsCount(int type);
	 
}
