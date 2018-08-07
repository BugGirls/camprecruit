package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.AssociationDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.Association;
import com.jeefw.service.sys.AssociationService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class AssociationServiceImpl extends BaseService<Association> implements AssociationService {

	private AssociationDao tzzAssociationDao;

	@Resource
	public void setAssociationDao(AssociationDao tzzAssociationDao) {
		this.tzzAssociationDao = tzzAssociationDao;
		this.dao = tzzAssociationDao;
	}
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Override
	public void deleteAssociationById(int tdId) {
		tzzAssociationDao.deleteByPK(tdId);
		
	}

	@Override
	public List<Association> getAssociationListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<Association> list = this.tzzAssociationDao.getAssociationListByIds(ids);
		return list;
	}

	@Override
	public List<Association> queryHotByorder(String order) { 
		String hql="from Association ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by sales desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by sales desc";
		}else {
			hql+=" order by createTime desc,sales desc";
		}
		return tzzAssociationDao.queryHotByorder( hql); 
	}

	@Override
	public int pageAllByorder(String order) { 
		String hql="from Association ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzAssociationDao.pageAllByorder( hql ); 
	} 	 
	
	@Override
	public List<Association> queryAllByorder(String order,int page) {
		page=(page-1)*8;
		String hql="from Association ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzAssociationDao.queryAllByorder( hql,page); 
	}

	@Override
	public List<Association> searchAssociation(int family, int page, String order) {  
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from Association where 1=1 ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(family!=0){ 
			String level=tzzDictionaryDao.get(family).getLevel();
			if(level.equals("1")){
				hql.append("and familyOne = ? ");
			}else if(level.equals("2")){
				hql.append("and familyTwo = ? ");
			}else if(level.equals("3")){
				hql.append("and familyId = ? ");
			}
		}
		if(order.equals("1")){
			hql.append(" and  rexiao=1 order by createTime desc");
		}else  if(order.equals("2")){
			hql.append(" and  rexiao=2 order by createTime desc");
		}else {
			hql.append(" order by createTime desc");
		}
		return tzzAssociationDao.searchAssociation( hql.toString(),family,page); 
		 
	}

	@Override
	public int searchAssociationPages(int family , String order) { 
	 
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from Association where 1=1 ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(family!=0){ 
			String level=tzzDictionaryDao.get(family).getLevel();
			if(level.equals("1")){
				hql.append("and familyOne = ? ");
			}else if(level.equals("2")){
				hql.append("and familyTwo = ? ");
			}else if(level.equals("3")){
				hql.append("and familyId = ? ");
			}
		}
		if(order.equals("1")){
			hql.append(" and rexiao=1 order by createTime desc");
		}else  if(order.equals("2")){
			hql.append(" and  rexiao=2 order by createTime desc");
		}else {
			hql.append(" order by createTime desc");
		}
		return tzzAssociationDao.searchAssociationPage( hql.toString(),family); 
	
	}
	@Override
	public List<Association> searchByName(String name, int page, String order) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from Association ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		}
		hql.append(" order by createTime desc");
		return tzzAssociationDao.searchByName( hql.toString(),name,page); 
		  
	}
	@Override
	public int searchByNamepages(String name) {
		 
		StringBuffer hql =new StringBuffer(); 
		hql.append(" from Association ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		} 
		return tzzAssociationDao.searchByNameNum( hql.toString(),name ); 
		 
	}
	@Override
	public List<Association> searchAssociation(int page,int type) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from Association ");
		hql.append("where  type=").append(type);
		hql.append(" order by id desc");
		
		return tzzAssociationDao.getindexList(hql.toString(),page);
	}
	@Override
	public List<Association> queryTopInformationHTMLList(List<Association> resultList) {
		List<Association> safetyNewsList = new ArrayList<Association>();
		for (Association entity : resultList) {
			Association safetyNews = new Association();
			safetyNews.setId(entity.getId());
			safetyNews.setLogo(entity.getLogo()); 
			if(entity.getName().length()>25){
				safetyNews.setName(entity.getName().substring(0,25)+"...");
			}else {
				safetyNews.setName(entity.getName());
			}
			safetyNews.setCreater(entity.getCreater());
			safetyNews.setCreatetime(entity.getCreatetime());
			safetyNewsList.add(safetyNews);
		}
		return safetyNewsList;
	}
	@Override
	public Integer searchAssociationCount(int type) {
		StringBuffer hql =new StringBuffer();
		hql.append("select count(*) from Association ");
		hql.append("where  type=").append(type);
		
		return tzzAssociationDao.getindexListCount(hql.toString());
	}

	@Override
	public Map<Integer, String> getAssociationMap() {
		return tzzAssociationDao.getAssociationMap();
	} 	 

}
