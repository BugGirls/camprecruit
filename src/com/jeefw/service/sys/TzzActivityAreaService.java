package com.jeefw.service.sys;
 
import java.util.List;

import com.jeefw.model.sys.TzzActivityArea;
import com.jeefw.model.sys.TzzDictionary;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface TzzActivityAreaService extends Service<TzzActivityArea> {

	void deleteTzzActivityAreaById(int tdId);
	
	 List<TzzActivityArea> queryTzzActivityAreaCnList(List<TzzActivityArea> resultList,List<TzzDictionary> dict);
	 List<TzzActivityArea> queryHotByorder(String order);
	 List<TzzActivityArea> getActivityAreaListByIds(List<Integer> ids);
	 List<TzzActivityArea> queryAllByorder(String order,int page);
	 int pageAllByorder(String order);
	
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	 List<TzzActivityArea> searchByName(String name , int page , String order );
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
	 List<TzzActivityArea> searchActivityArea(int  family,int page, String order);//
	 /**
	  * 分类查询总页数
	  * @param family
	  * @param order
	  * @return
	  */
	 int searchActivityAreaPages(int  family, String order);
	 
	 List<TzzActivityArea> searchActivityArea(int pagenum);

	List<TzzActivityArea> queryTopInformationHTMLList(List<TzzActivityArea> ilist);

	Integer searchActivityAreaCount();
	 
}
