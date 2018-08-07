package com.jeefw.service.sys;
 
import java.util.List;
import java.util.Map;

import com.jeefw.model.sys.Association;

import core.service.Service;

/**
 * 角色的业务逻辑层的接口
 * @ 
 */
public interface AssociationService extends Service<Association> {

	void deleteAssociationById(int tdId);
	
	 List<Association> queryHotByorder(String order);
	 List<Association> getAssociationListByIds(List<Integer> ids);
	 List<Association> queryAllByorder(String order,int page);
	 int pageAllByorder(String order);
	
	 /**
	  * 根据名称模糊查询
	  * @param name
	  * @param order
	  * @return
	  */
	 List<Association> searchByName(String name , int page , String order );
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
	 List<Association> searchAssociation(int  family,int page, String order);//
	 /**
	  * 分类查询总页数
	  * @param family
	  * @param order
	  * @return
	  */
	 int searchAssociationPages(int  family, String order);
	 
	 List<Association> searchAssociation(int pagenum, int type);

	List<Association> queryTopInformationHTMLList(List<Association> ilist);

	Integer searchAssociationCount(int type);

	Map<Integer, String> getAssociationMap();
	 
}
