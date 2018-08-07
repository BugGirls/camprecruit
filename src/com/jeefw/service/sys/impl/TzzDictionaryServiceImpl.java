package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.service.sys.TzzDictionaryService;

import core.service.BaseService;

/**
 * 角色的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzDictionaryServiceImpl extends BaseService<TzzDictionary> implements TzzDictionaryService {

	private TzzDictionaryDao tzzDictionaryDao;

	@Resource
	public void setTzzDictionaryDao(TzzDictionaryDao tzzDictionaryDao) {
		this.tzzDictionaryDao = tzzDictionaryDao;
		this.dao = tzzDictionaryDao;
	}

	@Override
	public void deleteTzzDicById(int tdId) {
		tzzDictionaryDao.deleteTzzDicById(tdId);
		
	}
	
	public List<TzzDictionary> queryTzzDictCnList(List<TzzDictionary> resultList) {
		List<TzzDictionary> tzzUserList = new ArrayList<TzzDictionary>();
		for (TzzDictionary entity : resultList) {
			TzzDictionary tzzDict = new TzzDictionary();
			tzzDict.setId(entity.getId());
			tzzDict.setName(entity.getName());
			tzzDict.setTitle(entity.getTitle());			
			tzzDict.setType(entity.getType());
			tzzDict.setParentId(entity.getParentId());
			String level = entity.getLevel();
			if("1".equals(level)){
				tzzDict.setLevel("一级分类");
			}else if ("2".equals(level)) {
				tzzDict.setLevel("二级分类");
			} else if ("3".equals(level)) {
				tzzDict.setLevel("三级分类");
			}
			tzzDict.setCreateTime(entity.getCreateTime());			
			tzzUserList.add(tzzDict);
		}
		return tzzUserList;	
	}

	@Override
	public List<TzzDictionary> getDictionaryByids(List<Integer> ids) {
		List<TzzDictionary> list = this.tzzDictionaryDao.getDictionaryByids(ids);
		return list;
	} 	 

}
