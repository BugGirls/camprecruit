package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
   
 



import com.jeefw.dao.sys.TzzAnswerDao;
import com.jeefw.dao.sys.TzzDictionaryDao; 
import com.jeefw.dao.sys.TzzUserDao;
import com.jeefw.model.sys.TzzAnswer; 
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.TzzAnswerService; 

import core.service.BaseService;
import core.util.HtmlUtils;
import core.util.PTUtils;

/**
 * 信息发布的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzAnswerServiceImpl extends BaseService<TzzAnswer> implements TzzAnswerService {

	private TzzAnswerDao tzzAnswerDao;

	@Resource
	private TzzUserDao tzzUserDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	public void setTzzAnswerDao(TzzAnswerDao tzzAnswerDao) {
		this.tzzAnswerDao = tzzAnswerDao;
		this.dao = tzzAnswerDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<TzzAnswer> queryTzzAnswerHTMLList(List<TzzAnswer> resultList) {
		List<TzzAnswer> tzzAnswerList = new ArrayList<TzzAnswer>();
		for (TzzAnswer entity : resultList) {
			TzzAnswer tzzAnswer = new TzzAnswer();
			tzzAnswer.setId(entity.getId());
			tzzAnswer.setQuestionId(entity.getQuestionId());
			int id=entity.getUserId();
			if(entity.getUserId()!=0){
				tzzAnswer.setUsername(tzzUserDao.get(entity.getUserId()).getRealname());
			}else {
				tzzAnswer.setUsername("管理员");
			} 
			tzzAnswer.setCreateTime(entity.getCreateTime());
			if(entity.getAdopt().equals("1")){
				tzzAnswer.setAdoptcn("以采纳");
			}else {
				tzzAnswer.setAdoptcn("未采纳");
			} 
			tzzAnswer.setAdopt(entity.getAdopt()); 
			tzzAnswer.setZan(entity.getZan());
			tzzAnswer.setContent(entity.getContent());
			tzzAnswer.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			tzzAnswerList.add(tzzAnswer);
		}
		return tzzAnswerList;
	}
	//回复表
	public List<TzzAnswer> getTzzAnswerlist(List<TzzAnswer> resultList,int pagenum,int pagesize) {
		List<TzzAnswer> tzzAnswerList = new ArrayList<TzzAnswer>();
		int  fir=(pagenum-1)*pagesize;
		int end=fir+pagesize;
		if(resultList.size()<fir+pagesize){
			end=resultList.size();
		}
		for (int i=fir;i<end ;i++) {
			TzzAnswer entity=new TzzAnswer();
			entity=resultList.get(i);
			TzzAnswer tzzAnswer = new TzzAnswer();
			tzzAnswer.setId(entity.getId());
			tzzAnswer.setZan(entity.getZan());
			tzzAnswer.setType(entity.getType());
			tzzAnswer.setAnswerId(entity.getAnswerId());
			tzzAnswer.setQuestionId(entity.getQuestionId());
			int id=entity.getUserId();
			if(entity.getUserId()!=0){
				TzzUser tzzUser=new TzzUser();
				tzzUser=tzzUserDao.get(entity.getUserId());
				tzzAnswer.setUsername(tzzUser.getRealname());
				tzzAnswer.setUserimg(tzzUser.getImage());
			}else {
				tzzAnswer.setUsername("管理员");
				tzzAnswer.setUserimg("/static/dist/img/admin.png");
			} 
			tzzAnswer.setTime(PTUtils.gettime(entity.getCreateTime()));
			if(entity.getAdopt().equals("1")){
				tzzAnswer.setAdoptcn("以采纳");
			}else {
				tzzAnswer.setAdoptcn("未采纳");
			} 
			tzzAnswer.setAdopt(entity.getAdopt()); 
			tzzAnswer.setContent(entity.getContent());
			tzzAnswer.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			tzzAnswerList.add(tzzAnswer);
		}
		return tzzAnswerList;
	}
	

	// 生成信息的索引

	public void indexingTzzAnswer() {
		tzzAnswerDao.indexingTzzAnswer();
	}

	// 全文检索信息

	public List<TzzAnswer> queryByTzzAnswerName(String name) {
		return tzzAnswerDao.queryByTzzAnswerName(name);
	}
 
}
