package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.CooperationCollegesDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.CooperationColleges;
import com.jeefw.service.sys.CooperationCollegesService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class CooperationCollegesServiceImpl extends BaseService<CooperationColleges> implements CooperationCollegesService {

	private CooperationCollegesDao tzzCooperationCollegesDao;

	@Resource
	public void setCooperationCollegesDao(CooperationCollegesDao tzzCooperationCollegesDao) {
		this.tzzCooperationCollegesDao = tzzCooperationCollegesDao;
		this.dao = tzzCooperationCollegesDao;
	}
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Override
	public void deleteCooperationCollegesById(int tdId) {
		tzzCooperationCollegesDao.deleteByPK(tdId);
		
	}

	@Override
	public List<CooperationColleges> getCooperationCollegesListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<CooperationColleges> list = this.tzzCooperationCollegesDao.getCooperationCollegesListByIds(ids);
		return list;
	}

	@Override
	public List<CooperationColleges> queryHotByorder(String order) { 
		String hql="from CooperationColleges ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by sales desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by sales desc";
		}else {
			hql+=" order by createTime desc,sales desc";
		}
		return tzzCooperationCollegesDao.queryHotByorder( hql); 
	}

	@Override
	public int pageAllByorder(String order) { 
		String hql="from CooperationColleges ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzCooperationCollegesDao.pageAllByorder( hql ); 
	} 	 
	
	@Override
	public List<CooperationColleges> queryAllByorder(String order,int page) {
		page=(page-1)*8;
		String hql="from CooperationColleges ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzCooperationCollegesDao.queryAllByorder( hql,page); 
	}

	@Override
	public List<CooperationColleges> searchCooperationColleges(int family, int page, String order) {  
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from CooperationColleges where 1=1 ");
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
		return tzzCooperationCollegesDao.searchCooperationColleges( hql.toString(),family,page); 
		 
	}

	@Override
	public int searchCooperationCollegesPages(int family , String order) { 
	 
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from CooperationColleges where 1=1 ");
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
		return tzzCooperationCollegesDao.searchCooperationCollegesPage( hql.toString(),family); 
	
	}
	@Override
	public List<CooperationColleges> searchByName(String name, int page, String order) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from CooperationColleges ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		}
		hql.append(" order by createTime desc");
		return tzzCooperationCollegesDao.searchByName( hql.toString(),name,page); 
		  
	}
	@Override
	public int searchByNamepages(String name) {
		 
		StringBuffer hql =new StringBuffer(); 
		hql.append(" from CooperationColleges ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		} 
		return tzzCooperationCollegesDao.searchByNameNum( hql.toString(),name ); 
		 
	}
	@Override
	public List<CooperationColleges> searchCooperationColleges(int page,int type) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from CooperationColleges ");
		hql.append("where  type=").append(type);
		hql.append(" order by id desc");
		
		return tzzCooperationCollegesDao.getindexList(hql.toString(),page);
	}
	@Override
	public List<CooperationColleges> queryTopInformationHTMLList(List<CooperationColleges> resultList) {
		List<CooperationColleges> safetyNewsList = new ArrayList<CooperationColleges>();
		for (CooperationColleges entity : resultList) {
			CooperationColleges safetyNews = new CooperationColleges();
			safetyNews.setId(entity.getId());
			safetyNews.setLogo(entity.getLogo()); 
			if(entity.getName().length()>25){
				safetyNews.setName(entity.getName().substring(0,25)+"...");
			}else {
				safetyNews.setName(entity.getName());
			}
			safetyNews.setType(entity.getType());
			safetyNews.setCreater(entity.getCreater());
			safetyNews.setCreatetime(entity.getCreatetime());
			safetyNews.setContent(HtmlUtils.htmltoText(entity.getContent()));
			 
			if(safetyNews.getContent().length()>80){
				safetyNews.setContent(safetyNews.getContent().substring(0,80)+"...");
			}
			safetyNewsList.add(safetyNews);
		}
		return safetyNewsList;
	}
	@Override
	public Integer searchCooperationCollegesCount(int type) {
		StringBuffer hql =new StringBuffer();
		hql.append("select count(*) from CooperationColleges ");
		hql.append("where  type=").append(type);
		
		return tzzCooperationCollegesDao.getindexListCount(hql.toString());
	}

	@Override
	public Map<Integer, String> getCooperationCollegesMap() {
		return tzzCooperationCollegesDao.getCooperationCollegesMap();
	} 	 

}
