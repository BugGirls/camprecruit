package com.jeefw.service.sys;
 
import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.CooperationColleges;

import core.service.Service;

/**
 * 合作院校的业务逻辑层的接口
 * @ 
 */
public interface CooperationCollegesService extends Service<CooperationColleges> {

	void deleteCooperationCollegesById(int tdId);
	
	 List<CooperationColleges> queryHotByorder(String order);
	 List<CooperationColleges> getCooperationCollegesListByIds(List<Integer> ids);
	 List<CooperationColleges> queryAllByorder(String order,int page);
	 int pageAllByorder(String order);
	
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	 List<CooperationColleges> searchByName(String name , int page , String order );
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
	 List<CooperationColleges> searchCooperationColleges(int  family,int page, String order);//
	 /**
	  * 分类查询总页数
	  * @param family
	  * @param order
	  * @return
	  */
	 int searchCooperationCollegesPages(int  family, String order);
	 
	 List<CooperationColleges> searchCooperationColleges(int pagenum, int type);

	List<CooperationColleges> queryTopInformationHTMLList(List<CooperationColleges> ilist);

	Integer searchCooperationCollegesCount(int type);

	Map<Integer, String> getCooperationCollegesMap();
	 
}
