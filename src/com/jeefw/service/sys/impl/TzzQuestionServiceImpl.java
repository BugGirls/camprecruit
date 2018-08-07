package com.jeefw.service.sys.impl;

import java.util.ArrayList; 
import java.util.List; 

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
    
import com.jeefw.dao.sys.TzzAnswerDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzQuestionDao;
import com.jeefw.dao.sys.TzzUserDao; 
import com.jeefw.model.sys.TzzDictionary;
import com.jeefw.model.sys.TzzQuestion;
import com.jeefw.model.sys.TzzUser; 
import com.jeefw.service.sys.TzzQuestionService;

import core.service.BaseService;
import core.util.HtmlUtils;
import core.util.PTUtils;

/**
 * 信息发布的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzQuestionServiceImpl extends BaseService<TzzQuestion> implements TzzQuestionService {

	private TzzQuestionDao tzzQuestionDao;

	@Resource
	private TzzUserDao tzzUserDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	private TzzAnswerDao tzzAnswerDao;
	@Resource
	public void setTzzQuestionDao(TzzQuestionDao tzzQuestionDao) {
		this.tzzQuestionDao = tzzQuestionDao;
		this.dao = tzzQuestionDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

	public List<TzzQuestion> queryTzzQuestionHTMLList(List<TzzQuestion> resultList) {
		List<TzzQuestion> tzzQuestionList = new ArrayList<TzzQuestion>();
		for (TzzQuestion entity : resultList) {
			TzzQuestion tzzQuestion = new TzzQuestion();
			tzzQuestion.setId(entity.getId());
			tzzQuestion.setTitle(entity.getTitle());
			tzzQuestion.setUserId(entity.getUserId());
			tzzQuestion.setUsername(tzzUserDao.get(entity.getUserId()).getUsername());
			tzzQuestion.setCreateTime(entity.getCreateTime());
			//tzzQuestion.setFamilyId(entity.getFamilyId());
			if(tzzDictionaryDao.get(entity.getFamilyId())!=null){
				tzzQuestion.setFamilyname(tzzDictionaryDao.get(entity.getFamilyId()).getName());
			} 
			if(entity.getEssence().equals("1")){
				tzzQuestion.setEssencecn("精华");
			}else{
				tzzQuestion.setEssencecn("普通");
			}
			if(entity.getTop().equals("1")){
				tzzQuestion.setTopcn("已置顶");
			}else {
				tzzQuestion.setTopcn("未置顶");
			}
			tzzQuestion.setEssence(entity.getEssence()); 
			
			if(entity.getState().equals("1")){
				tzzQuestion.setStatecn("有回复");
			}else if(entity.getState().equals("2")){
				tzzQuestion.setStatecn("已解决");
			}else{
				tzzQuestion.setStatecn("无解答");
			} 
			tzzQuestion.setBrowseNum(entity.getBrowseNum()); 
			tzzQuestion.setContent(entity.getContent());
			tzzQuestion.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			tzzQuestionList.add(tzzQuestion);
		}
		return tzzQuestionList;
	}

	// 生成信息的索引

	public void indexingTzzQuestion() {
		tzzQuestionDao.indexingTzzQuestion();
	}

	// 全文检索信息

	public List<TzzQuestion> queryByTzzQuestionName(String name) {
		return tzzQuestionDao.queryByTzzQuestionName(name);
	}

	/**
	 * 首页数据
	 */
	@Override
	public List<TzzQuestion> getstaticQuestionslist( int num) { 
		String hql="from TzzQuestion order by top desc, essence desc ,createTime desc";
		List<TzzQuestion> qlist= tzzQuestionDao.getstaticQuestionslist(hql , num);
		List<TzzQuestion> nqlist = new ArrayList<>(); 
		for (TzzQuestion entity : qlist) {
			TzzQuestion tzzQuestion=new TzzQuestion();
			int anum=tzzAnswerDao.queryByProerties("questionId",entity.getId() ).size();
			if(anum >1000){ 
				tzzQuestion.setAnswernum(anum/1000+"K");
			}else {
				tzzQuestion.setAnswernum(anum+"");
			} 
			if(entity.getBrowseNum()>1000){
				tzzQuestion.setClicknumstr(entity.getBrowseNum()/1000+"K"); 
			}else {
				tzzQuestion.setClicknumstr(entity.getBrowseNum()+""); 
			}
			tzzQuestion.setTop(entity.getTop());
			tzzQuestion.setEssence(entity.getEssence());
			
			tzzQuestion.setState(entity.getState()); 
			tzzQuestion.setId(entity.getId());
			if(entity.getTitle().length()>30){
				tzzQuestion.setTitle(entity.getTitle().substring(0, 30)+"..."); 
			}else{
				tzzQuestion.setTitle(entity.getTitle());
			}
			
			tzzQuestion.setTime(PTUtils.gettime(entity.getCreateTime()));
			tzzQuestion.setUsername(tzzUserDao.get(entity.getUserId()).getUsername()); 
			nqlist.add(tzzQuestion);
		}
		return nqlist;
	}

	/**
	 * 热门
	 */
	@Override
	public List<TzzQuestion> gethotQuestionslist(int num) {
		String hql="from TzzQuestion order by browseNum desc,createTime desc";
		List<TzzQuestion> qlist= tzzQuestionDao.getstaticQuestionslist(hql , num);
		List<TzzQuestion> nqlist = new ArrayList<>(); 
		for (int i = 0; i < qlist.size(); i++) {
			TzzQuestion tzzQuestion=new TzzQuestion();
			tzzQuestion.setId(qlist.get(i).getId());
			if(qlist.get(i).getTitle().length()>15){
				tzzQuestion.setTitle(qlist.get(i).getTitle().substring(0, 15)+"..."); 
			}else{
				tzzQuestion.setTitle(qlist.get(i).getTitle());
			} 
			nqlist.add(tzzQuestion);
		}
		return nqlist;
	}
/**
 * 搜索分页
 */
	@Override
	public List<TzzQuestion> searchQuestionslist(String family, String order, int pagenum, int size) {
		List<TzzDictionary> tdlist=tzzDictionaryDao.doQueryAll();
		List<Integer> fs=new ArrayList<Integer>();
		 fs.add(0);
		StringBuffer hql=new StringBuffer(); 
		if(family.equals("0")){
			hql.append("from TzzQuestion where 1=1 ");
			
		}else {
			String level= tzzDictionaryDao.get(Integer.parseInt(family)).getLevel();
			
			if(level.equals("1")){
				for (int i = 0; i < tdlist.size(); i++) {
					 if(tdlist.get(i).getParentId()==Integer.parseInt(family)){
						 for (int j = 0; j < tdlist.size(); j++) {
							 if(tdlist.get(j).getParentId()==tdlist.get(i).getId()){
								 fs.add(tdlist.get(j).getId());				
							 }
						}
					 }; 
				}
			}else if(level.equals("2")){
				for (int i = 0; i < tdlist.size(); i++) {
					 if(tdlist.get(i).getParentId()==Integer.parseInt(family)){ 
						fs.add(tdlist.get(i).getId());	 
					 }; 
				}
			}else {
				fs.add(Integer.parseInt(family));	 
			} 
			hql.append("from TzzQuestion where familyId in (:alist)  ");
		}
		if(order.equals("1")){
			hql.append(" order by top desc, essence desc,createTime desc");
		}else if(order.equals("2")){
			hql.append(" and state='0' order by top desc ,createTime desc");
		}else if(order.equals("3")){
			hql.append(" order by createTime desc");
		}
		pagenum=(pagenum-1)*9;
		List<TzzQuestion> qlist= tzzQuestionDao.searchQuestionslist(hql.toString(), fs, pagenum, size);
		List<TzzQuestion> nqlist = new ArrayList<>(); 
		for (TzzQuestion entity : qlist) {
			TzzQuestion tzzQuestion=new TzzQuestion();
			int anum=tzzAnswerDao.queryByProerties("questionId",entity.getId() ).size();
			if(anum >1000){ 
				tzzQuestion.setAnswernum(anum/1000+"K");
			}else {
				tzzQuestion.setAnswernum(anum+"");
			} 
			if(entity.getBrowseNum()>1000){
				tzzQuestion.setClicknumstr(entity.getBrowseNum()/1000+"K"); 
			}else {
				tzzQuestion.setClicknumstr(entity.getBrowseNum()+""); 
			}
			tzzQuestion.setTop(entity.getTop());
			tzzQuestion.setEssence(entity.getEssence());
			tzzQuestion.setState(entity.getState()); 
			tzzQuestion.setId(entity.getId());
			if(entity.getTitle().length()>35){
				tzzQuestion.setTitle(entity.getTitle().substring(0, 35)+"..."); 
			}else{
				tzzQuestion.setTitle(entity.getTitle());
			} 
			tzzQuestion.setTime(PTUtils.gettime(entity.getCreateTime()));
			tzzQuestion.setUsername(tzzUserDao.get(entity.getUserId()).getUsername()); 
			nqlist.add(tzzQuestion);
		}
		return nqlist; 
	}
/**
 * 问答详情
 */
@Override
public TzzQuestion getQuestion(int qid) {
	TzzQuestion entity=tzzQuestionDao.get(qid);
	TzzQuestion tzzQuestion = new TzzQuestion();
	tzzQuestion.setId(entity.getId());
	tzzQuestion.setTitle(entity.getTitle());
	tzzQuestion.setUserId(entity.getUserId());
	TzzUser tzzUser=new TzzUser();
	tzzUser=tzzUserDao.get(entity.getUserId());
	tzzQuestion.setUsername(tzzUser.getUsername());
	tzzQuestion.setUserimg(tzzUser.getImage());
	tzzQuestion.setTime(PTUtils.gettime(entity.getCreateTime()));
	if(tzzDictionaryDao.get(entity.getFamilyId())!=null){
		tzzQuestion.setFamilyname(tzzDictionaryDao.get(entity.getFamilyId()).getName());
	}  
	int anum=tzzAnswerDao.queryByProerties("questionId",entity.getId() ).size();
	if(anum >1000){ 
		tzzQuestion.setAnswernum(anum/1000+"K");
	}else {
		tzzQuestion.setAnswernum(anum+"");
	} 
	if(entity.getBrowseNum()>1000){
		tzzQuestion.setClicknumstr(entity.getBrowseNum()/1000+"K"); 
	}else {
		tzzQuestion.setClicknumstr(entity.getBrowseNum()+""); 
	}
	tzzQuestion.setEssence(entity.getEssence());
  
	tzzQuestion.setTop(entity.getTop());
	   
	tzzQuestion.setState(entity.getState());
	  
	tzzQuestion.setContent(entity.getContent());
	tzzQuestion.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
	
	
	return tzzQuestion;
}

@Override
public int searchQuestionspagenum(String family, String order,int size) {
	List<TzzDictionary> tdlist=tzzDictionaryDao.doQueryAll();
	List<Integer> fs=new ArrayList<Integer>();
	 fs.add(0);
	StringBuffer hql=new StringBuffer(); 
	if(family.equals("0")){
		hql.append("from TzzQuestion where 1=1 ");
		
	}else {
		String level= tzzDictionaryDao.get(Integer.parseInt(family)).getLevel();
		
		if(level.equals("1")){
			for (int i = 0; i < tdlist.size(); i++) {
				 if(tdlist.get(i).getParentId()==Integer.parseInt(family)){
					 for (int j = 0; j < tdlist.size(); j++) {
						 if(tdlist.get(j).getParentId()==tdlist.get(i).getId()){
							 fs.add(tdlist.get(j).getId());				
						 }
					}
				 }; 
			}
		}else if(level.equals("2")){
			for (int i = 0; i < tdlist.size(); i++) {
				 if(tdlist.get(i).getParentId()==Integer.parseInt(family)){ 
					fs.add(tdlist.get(i).getId());	 
				 }; 
			}
		}else {
			fs.add(Integer.parseInt(family));	 
		} 
		hql.append("from TzzQuestion where familyId in (:alist)  ");
	}
	if(order.equals("1")){
		hql.append(" order by top desc, essence desc,createTime desc");
	}else if(order.equals("2")){
		hql.append(" and state='0' order by top desc ,createTime desc");
	}else if(order.equals("3")){
		hql.append(" order by createTime desc");
	}
	List<TzzQuestion> qlist= tzzQuestionDao.searchQuestionspagenum(hql.toString(), fs);
	 int lnum=qlist.size();
	 int num=0;
	 if(lnum%size==0){
		 num=lnum/size;
	 }else{
		 num=lnum/size+1;
	 }
	 
	return num;
}

}
