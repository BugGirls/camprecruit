package com.jeefw.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
 
 
import com.jeefw.dao.sys.TzzAnswerDao; 
import com.jeefw.model.sys.TzzAnswer; 

import core.dao.BaseDao;
import core.util.HtmlUtils;

/**
 * 信息发布的数据持久层的实现类
 * @ 
 */
@Repository
public class TzzAnswerDaoImpl extends BaseDao<TzzAnswer> implements TzzAnswerDao
{

	public TzzAnswerDaoImpl() {
		super(TzzAnswer.class);
	}

	// 生成信息的索引
	public void indexingTzzAnswer() {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getSession());
			// Object information = fullTextSession.load(Information.class, new BigDecimal(99));
			// fullTextSession.index(information);
			fullTextSession.createIndexer(TzzAnswer.class).threadsForSubsequentFetching(1).threadsToLoadObjects(1).startAndWait();
			fullTextSession.flushToIndexes();
			fullTextSession.getSearchFactory().optimize();
			fullTextSession.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全文检索信息
	public List<TzzAnswer> queryByTzzAnswerName(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		SearchFactory searchFactory = fullTextSession.getSearchFactory();
		final QueryBuilder queryBuilder = searchFactory.buildQueryBuilder().forEntity(TzzAnswer.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.bool()
				.should(queryBuilder.keyword().onField("content").matching(name).createQuery()).createQuery();
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, TzzAnswer.class).setMaxResults(500);

		List<TzzAnswer> originalTzzAnswerList = fullTextQuery.list();
		List<TzzAnswer>TzzAnswerList = new ArrayList<TzzAnswer>();
		for (TzzAnswer entity : originalTzzAnswerList) {
			TzzAnswer tzzAnswer = new TzzAnswer();
			tzzAnswer.setId(entity.getId()); 
			tzzAnswer.setQuestionId(entity.getQuestionId()); 
			tzzAnswer.setCreateTime(entity.getCreateTime());
			tzzAnswer.setUserId(entity.getUserId()); 
			tzzAnswer.setContentNoHTML(HtmlUtils.htmltoText(entity.getContent()));
			TzzAnswerList.add(tzzAnswer);
		}

		return TzzAnswerList;
	}

}
