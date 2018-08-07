package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzSafetyNewsDao;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzSafetyNews;
import com.jeefw.service.sys.TzzSafetyNewsService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzSafetyNewsServiceImpl extends BaseService<TzzSafetyNews> implements TzzSafetyNewsService {

	private TzzSafetyNewsDao tzzSafetyNewsDao;

	@Resource
	public void setTzzDictionaryDao(TzzSafetyNewsDao tzzSafetyNewsDao) {
		this.tzzSafetyNewsDao = tzzSafetyNewsDao;
		this.dao = tzzSafetyNewsDao;
	}
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Override
	public void deleteTzzSafetyNewsById(int tdId) {
		tzzSafetyNewsDao.deleteByPK(tdId);
		
	}
	// 对查到的数据进行封装。将类型的名字修改成为要显示的名字。
	public  List<TzzSafetyNews> queryTzzSafetyNewsCnList(List<TzzSafetyNews> resultList, List<TzzDictionary> dict){
		List<TzzSafetyNews> tzzSafetyNewsList = new ArrayList<TzzSafetyNews>();
		Map<Integer,TzzDictionary> tzzDictMap = new HashMap<Integer,TzzDictionary>() ;
		for(TzzDictionary entity: dict ){
			tzzDictMap.put(entity.getId(), entity);
		}
		
		
		for (TzzSafetyNews entity : resultList) {
//			TzzSafetyNews tzzSafetyNews = new TzzSafetyNews();
			Integer type = entity.getType();
			TzzDictionary dict_one = tzzDictMap.get(type);
			if (dict_one != null){ 
				entity.setTypeName(dict_one.getName());
			} 	
			Integer origin = entity.getOrigin();
			TzzDictionary dict_two = tzzDictMap.get(origin);
			if (dict_two != null){ 
				entity.setOriginName(dict_two.getName());
			} 
			tzzSafetyNewsList.add(entity);
		}
		return tzzSafetyNewsList;	
	}

	@Override
	public List<TzzSafetyNews> getSafetyNewsListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<TzzSafetyNews> list = this.tzzSafetyNewsDao.getSafetyNewsListByIds(ids);
		return list;
	}

	@Override
	public List<TzzSafetyNews> queryHotByorder(String order) { 
		String hql="from TzzSafetyNews ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by sales desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by sales desc";
		}else {
			hql+=" order by createTime desc,sales desc";
		}
		return tzzSafetyNewsDao.queryHotByorder( hql); 
	}

	@Override
	public int pageAllByorder(String order) { 
		String hql="from TzzSafetyNews ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzSafetyNewsDao.pageAllByorder( hql ); 
	} 	 
	
	@Override
	public List<TzzSafetyNews> queryAllByorder(String order,int page) {
		page=(page-1)*8;
		String hql="from TzzSafetyNews ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzSafetyNewsDao.queryAllByorder( hql,page); 
	}

	@Override
	public List<TzzSafetyNews> searchSafetyNews(int family, int page, String order) {  
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from TzzSafetyNews where 1=1 ");
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
		return tzzSafetyNewsDao.searchSafetyNews( hql.toString(),family,page); 
		 
	}

	@Override
	public int searchSafetyNewsPages(int family , String order) { 
	 
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from TzzSafetyNews where 1=1 ");
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
		return tzzSafetyNewsDao.searchSafetyNewsPage( hql.toString(),family); 
	
	}
	@Override
	public List<TzzSafetyNews> searchByName(String name, int page, String order) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from TzzSafetyNews ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		}
		hql.append(" order by createTime desc");
		return tzzSafetyNewsDao.searchByName( hql.toString(),name,page); 
		  
	}
	@Override
	public int searchByNamepages(String name) {
		 
		StringBuffer hql =new StringBuffer(); 
		hql.append(" from TzzSafetyNews ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		} 
		return tzzSafetyNewsDao.searchByNameNum( hql.toString(),name ); 
		 
	}
	@Override
	public List<TzzSafetyNews> searchSafetyNews(int page,int type) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from TzzSafetyNews ");
		hql.append("where  type=").append(type);
		hql.append(" order by id desc");
		
		return tzzSafetyNewsDao.getindexList(hql.toString(),page);
	}
	@Override
	public List<TzzSafetyNews> queryTopInformationHTMLList(List<TzzSafetyNews> resultList) {
		List<TzzSafetyNews> safetyNewsList = new ArrayList<TzzSafetyNews>();
		for (TzzSafetyNews entity : resultList) {
			TzzSafetyNews safetyNews = new TzzSafetyNews();
			safetyNews.setId(entity.getId());
			safetyNews.setImg(entity.getImg()); 
			if(entity.getName().length()>25){
				safetyNews.setName(entity.getName().substring(0,25)+"...");
			}else {
				safetyNews.setName(entity.getName());
			}
			safetyNews.setType(entity.getType());
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
	public Integer searchSafetyNewsCount(int type) {
		StringBuffer hql =new StringBuffer();
		hql.append("select count(*) from TzzSafetyNews ");
		hql.append("where  type=").append(type);
		
		return tzzSafetyNewsDao.getindexListCount(hql.toString());
	} 	 

}
