package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.AllianceBusinessDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.AllianceBusiness;
import com.jeefw.service.sys.AllianceBusinessService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class AllianceBusinessServiceImpl extends BaseService<AllianceBusiness> implements AllianceBusinessService {

	private AllianceBusinessDao tzzAllianceBusinessDao;

	@Resource
	public void setAllianceBusinessDao(AllianceBusinessDao tzzAllianceBusinessDao) {
		this.tzzAllianceBusinessDao = tzzAllianceBusinessDao;
		this.dao = tzzAllianceBusinessDao;
	}
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Override
	public void deleteAllianceBusinessById(int tdId) {
		tzzAllianceBusinessDao.deleteByPK(tdId);
		
	}

	@Override
	public List<AllianceBusiness> getAllianceBusinessListByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<AllianceBusiness> list = this.tzzAllianceBusinessDao.getAllianceBusinessListByIds(ids);
		return list;
	}

	@Override
	public List<AllianceBusiness> queryHotByorder(String order) { 
		String hql="from AllianceBusiness ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by sales desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by sales desc";
		}else {
			hql+=" order by createTime desc,sales desc";
		}
		return tzzAllianceBusinessDao.queryHotByorder( hql); 
	}

	@Override
	public int pageAllByorder(String order) { 
		String hql="from AllianceBusiness ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzAllianceBusinessDao.pageAllByorder( hql ); 
	} 	 
	
	@Override
	public List<AllianceBusiness> queryAllByorder(String order,int page) {
		page=(page-1)*8;
		String hql="from AllianceBusiness ";
		if(order.equals("1")){
			hql+=" where rexiao=1 order by createTime desc";
		}else  if(order.equals("2")){
			hql+=" where rexiao=2 order by createTime desc";
		}else {
			hql+=" order by createTime desc";
		}
		return tzzAllianceBusinessDao.queryAllByorder( hql,page); 
	}

	@Override
	public List<AllianceBusiness> searchAllianceBusiness(int family, int page, String order) {  
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from AllianceBusiness where 1=1 ");
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
		return tzzAllianceBusinessDao.searchAllianceBusiness( hql.toString(),family,page); 
		 
	}

	@Override
	public int searchAllianceBusinessPages(int family , String order) { 
	 
		StringBuffer hql =new StringBuffer();
		System.out.println(order); 
		hql.append("from AllianceBusiness where 1=1 ");
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
		return tzzAllianceBusinessDao.searchAllianceBusinessPage( hql.toString(),family); 
	
	}
	@Override
	public List<AllianceBusiness> searchByName(String name, int page, String order) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from AllianceBusiness ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		}
		hql.append(" order by createTime desc");
		return tzzAllianceBusinessDao.searchByName( hql.toString(),name,page); 
		  
	}
	@Override
	public int searchByNamepages(String name) {
		 
		StringBuffer hql =new StringBuffer(); 
		hql.append(" from AllianceBusiness ");
		//"from TzzCourse where difficultyId=? order by createTime desc");
		if(name!=null && !name.equals("")){  
				hql.append(" where name like ? "); 
				hql.append(" or brief like ? "); 
				hql.append(" or content like ? "); 
		} 
		return tzzAllianceBusinessDao.searchByNameNum( hql.toString(),name ); 
		 
	}
	@Override
	public List<AllianceBusiness> searchAllianceBusiness(int page,int type) {
		page=(page-1)*9;
		StringBuffer hql =new StringBuffer();
		hql.append(" from AllianceBusiness ");
		hql.append("where  type=").append(type);
		hql.append(" order by id desc");
		
		return tzzAllianceBusinessDao.getindexList(hql.toString(),page);
	}
	@Override
	public List<AllianceBusiness> queryTopInformationHTMLList(List<AllianceBusiness> resultList) {
		List<AllianceBusiness> safetyNewsList = new ArrayList<AllianceBusiness>();
		for (AllianceBusiness entity : resultList) {
			AllianceBusiness safetyNews = new AllianceBusiness();
			safetyNews.setId(entity.getId());
			safetyNews.setLogo(entity.getLogo()); 
			if(entity.getName().length()>25){
				safetyNews.setName(entity.getName().substring(0,25)+"...");
			}else {
				safetyNews.setName(entity.getName());
			}
			safetyNews.setType(entity.getType());
			safetyNews.setTypeValue(entity.getTypeValue());
			safetyNews.setCreater(entity.getCreater());
			safetyNews.setCreatetime(entity.getCreatetime());
			safetyNews.setContent(HtmlUtils.htmltoText(entity.getContent()));
			safetyNews.setIndustry(entity.getIndustry());
			safetyNews.setIndustryValue(entity.getIndustryValue());
			safetyNews.setAddress(entity.getAddress());
			safetyNews.setAddressCoordinate(entity.getAddressCoordinate());
			safetyNews.setContacts(entity.getContacts());
			safetyNews.setContactsPhone(entity.getContactsPhone());
			safetyNews.setBusinessLicense(entity.getBusinessLicense());
			safetyNews.setProvince(entity.getProvince());
			safetyNews.setCity(entity.getCity());
			if(safetyNews.getContent().length()>80){
				safetyNews.setContent(safetyNews.getContent().substring(0,80)+"...");
			}
			safetyNewsList.add(safetyNews);
		}
		return safetyNewsList;
	}
	@Override
	public Integer searchAllianceBusinessCount(int type) {
		StringBuffer hql =new StringBuffer();
		hql.append("select count(*) from AllianceBusiness ");
		hql.append("where  type=").append(type);
		
		return tzzAllianceBusinessDao.getindexListCount(hql.toString());
	}

	@Override
	public Map<Integer, String> getAllianceBusinessMap() {
		return tzzAllianceBusinessDao.getAllianceBusinessMap();
	} 	 

}
