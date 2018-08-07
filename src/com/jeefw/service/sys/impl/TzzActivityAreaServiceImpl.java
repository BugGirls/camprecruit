package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzActivityAreaDao;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzActivityArea;
import com.jeefw.service.sys.TzzActivityAreaService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzActivityAreaServiceImpl extends BaseService<TzzActivityArea> implements TzzActivityAreaService {

	private TzzActivityAreaDao tzzActivityAreaDao;

	@Resource
	public void setTzzDictionaryDao(TzzActivityAreaDao tzzActivityAreaDao) {
		this.tzzActivityAreaDao = tzzActivityAreaDao;
		this.dao = tzzActivityAreaDao;
	}
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Override
	public void deleteTzzActivityAreaById(int tdId) {
		tzzActivityAreaDao.deleteByPK(tdId);
		
	}
	// 对查到的数据进行封装。将类型的名字修改成为要显示的名字。
	public  List<TzzActivityArea> queryTzzActivityAreaCnList(List<TzzActivityArea> resultList, List<TzzDictionary> dict){
		List<TzzActivityArea> tzzActivityAreaList = new ArrayList<TzzActivityArea>();
		Map<Integer,TzzDictionary> tzzDictMap = new HashMap<Integer,TzzDictionary>() ;
		for(TzzDictionary entity: dict ){
			tzzDictMap.put(entity.getId(), entity);
		}
		
		return tzzActivityAreaList;	
	}

	@Override
	public List<TzzActivityArea> getActivityAreaListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<TzzActivityArea> list = this.tzzActivityAreaDao.getActivityAreaListByIds(ids);
		return list;
	}

	@Override
	public List<TzzActivityArea> queryHotByorder(String order) { 
		String hql="from TzzActivityArea ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by sales desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by sales desc";
		}else {
			hql+=" order by createTime desc,sales desc";
		}
		return tzzActivityAreaDao.queryHotByorder( hql); 
	}

	@Override
	public int pageAllByorder(String order) { 
		String hql="from TzzActivityArea ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzActivityAreaDao.pageAllByorder( hql ); 
	} 	 
	
	@Override
	public List<TzzActivityArea> queryAllByorder(String order,int page) {
		page=(page-1)*8;
		String hql="from TzzActivityArea ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzActivityAreaDao.queryAllByorder( hql,page); 
	}

	@Override
	public List<TzzActivityArea> searchActivityArea(int family, int page, String order) {  
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from TzzActivityArea where 1=1 ")
		.append(" order by createTime desc");
		return tzzActivityAreaDao.searchActivityArea( hql.toString(),family,page); 
		 
	}

	@Override
	public int searchActivityAreaPages(int family , String order) { 
	 
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from TzzActivityArea where 1=1 ")
		.append(" order by createTime desc");
		return tzzActivityAreaDao.searchActivityAreaPage( hql.toString(),family); 
	
	}
	@Override
	public List<TzzActivityArea> searchByName(String name, int page, String order) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from TzzActivityArea ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		}
		hql.append(" order by createTime desc");
		return tzzActivityAreaDao.searchByName( hql.toString(),name,page); 
		  
	}
	@Override
	public int searchByNamepages(String name) {
		 
		StringBuffer hql =new StringBuffer(); 
		hql.append(" from TzzActivityArea ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		} 
		return tzzActivityAreaDao.searchByNameNum( hql.toString(),name ); 
		 
	}
	@Override
	public List<TzzActivityArea> searchActivityArea(int page) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from TzzActivityArea ");
		hql.append(" order by id desc");
		
		return tzzActivityAreaDao.getindexList(hql.toString(),page);
	}
	@Override
	public List<TzzActivityArea> queryTopInformationHTMLList(List<TzzActivityArea> resultList) {
		List<TzzActivityArea> safetyNewsList = new ArrayList<TzzActivityArea>();
		for (TzzActivityArea entity : resultList) {
			TzzActivityArea safetyNews = new TzzActivityArea();
			safetyNews.setId(entity.getId());
			safetyNews.setImg(entity.getImg()); 
			if(entity.getName().length()>25){
				safetyNews.setName(entity.getName().substring(0,25)+"...");
			}else {
				safetyNews.setName(entity.getName());
			}
			safetyNews.setCreater(entity.getCreater());
			safetyNews.setCreattime(entity.getCreattime());
			safetyNews.setContent(HtmlUtils.htmltoText(entity.getContent()));
			 
			if(safetyNews.getContent().length()>80){
				safetyNews.setContent(safetyNews.getContent().substring(0,80)+"...");
			}
			safetyNewsList.add(safetyNews);
		}
		return safetyNewsList;
	}
	@Override
	public Integer searchActivityAreaCount() {
		StringBuffer hql =new StringBuffer();
		hql.append("select count(*) from TzzActivityArea ");
		return tzzActivityAreaDao.getindexListCount(hql.toString());
	} 	 

}
