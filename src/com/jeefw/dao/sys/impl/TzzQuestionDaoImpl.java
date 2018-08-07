package com.jeefw.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
 





import com.jeefw.dao.sys.TzzQuestionDao;
import com.jeefw.model.sys.TzzQuestion;

import core.dao.BaseDao;
import core.util.HtmlUtils;

/**
 * 信息发布的数据持久层的实现类
 * @ 
 */
@Repository
public class TzzQuestionDaoImpl extends BaseDao<TzzQuestion> implements TzzQuestionDao
{

	public TzzQuestionDaoImpl() {
		super(TzzQuestion.class);
	}

	// 生成信息的索引
	public void indexingTzzQuestion() {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getSession());
			// Object information = fullTextSession.load(Information.class, new BigDecimal(99));
			// fullTextSession.index(information);
			fullTextSession.createIndexer(TzzQuestion.class).threadsForSubsequentFetching(1).threadsToLoadObjects(1).startAndWait();
			fullTextSession.flushToIndexes();
			fullTextSession.getSearchFactory().optimize();
			fullTextSession.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全文检索信息
	public List<TzzQuestion> queryByTzzQuestionName(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		SearchFactory searchFactory = fullTextSession.getSearchFactory();
		final QueryBuilder queryBuilder = searchFactory.buildQueryBuilder().forEntity(TzzQuestion.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.bool()
				.should(queryBuilder.keyword().onField("title").matching(name).createQuery())
				.should(queryBuilder.keyword().onField("content").matching(name).createQuery()).createQuery();
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, TzzQuestion.class).setMaxResults(500);

		List<TzzQuestion> originalTzzQuestionList = fullTextQuery.list();
		List<TzzQuestion>tzzQuestionList = new ArrayList<TzzQuestion>();
		for (TzzQuestion entity : originalTzzQuestionList) {
			TzzQuestion tzzQuestion = new TzzQuestion();
			tzzQuestion.setId(entity.getId());
			tzzQuestion.setTitle(entity.getTitle());
			tzzQuestion.setUserId(entity.getUserId());
			tzzQuestion.setCreateTime(entity.getCreateTime());
			tzzQuestion.setFamilyId(entity.getFamilyId());
			tzzQuestion.setEssence(entity.getEssence());
			tzzQuestion.setState(entity.getState());
			tzzQuestion.setTop(entity.getTop());
			tzzQuestion.setBrowseNum(entity.getBrowseNum()); 
			tzzQuestion.setContent(entity.getContent());
			tzzQuestion.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			tzzQuestionList.add(tzzQuestion);
		}

		return tzzQuestionList;
	}

	@Override
	public List<TzzQuestion> getstaticQuestionslist(String hql, int size) { 
			Query query = this.getSession().createQuery(hql); 
		 	query.setMaxResults(size); 
			return query.list(); 
	}

	//分页
	@Override
	public List<TzzQuestion> searchQuestionslist(String hql, List<Integer> fs, int page, int size) {
		Query query = this.getSession().createQuery(hql); 
	 	if(fs.size()>1){
	 		query.setParameterList("alist", fs); 
	 	}
		query.setMaxResults(size); 
	 	query.setFirstResult(page);
		return query.list(); 
	}

	@Override
	public List<TzzQuestion> searchQuestionspagenum(String hql, List<Integer> fs) {
		Query query = this.getSession().createQuery(hql); 
	 	if(fs.size()>1){
	 		query.setParameterList("alist", fs); 
	 	} 
		return query.list();  
	}

}
