package com.jeefw.service.sys;
 
import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.AllianceBusiness;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface AllianceBusinessService extends Service<AllianceBusiness> {

	void deleteAllianceBusinessById(int tdId);
	
	 List<AllianceBusiness> queryHotByorder(String order);
	 List<AllianceBusiness> getAllianceBusinessListByIds(List<Integer> ids);
	 List<AllianceBusiness> queryAllByorder(String order,int page);
	 int pageAllByorder(String order);
	
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	 List<AllianceBusiness> searchByName(String name , int page , String order );
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
	 List<AllianceBusiness> searchAllianceBusiness(int  family,int page, String order);//
	 /**
	  * 分类查询总页数
	  * @param family
	  * @param order
	  * @return
	  */
	 int searchAllianceBusinessPages(int  family, String order);
	 
	 List<AllianceBusiness> searchAllianceBusiness(int pagenum, int type);

	List<AllianceBusiness> queryTopInformationHTMLList(List<AllianceBusiness> ilist);

	Integer searchAllianceBusinessCount(int type);

	Map<Integer, String> getAllianceBusinessMap();
	 
}
