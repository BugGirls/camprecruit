package com.jeefw.dao.sys.impl;
 
import java.util.List;
 
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search; 
import org.springframework.stereotype.Repository;
 

import com.jeefw.dao.sys.TzzJianjieDao;
import com.jeefw.model.sys.TzzJianjie;

import core.dao.BaseDao; 

/**
 * 信息发布的数据持久层的实现类
 * @ 
 */
@Repository
public class TzzJianjieDaoImpl extends BaseDao<TzzJianjie> implements TzzJianjieDao {

	public TzzJianjieDaoImpl() {
		super(TzzJianjie.class);
	}

	// 生成信息的索引
	public void indexingTzzJianjie() {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getSession());
			// Object information = fullTextSession.load(Information.class, new BigDecimal(99));
			// fullTextSession.index(information);
			fullTextSession.createIndexer(TzzJianjie.class).threadsForSubsequentFetching(1).threadsToLoadObjects(1).startAndWait();
			fullTextSession.flushToIndexes();
			fullTextSession.getSearchFactory().optimize();
			fullTextSession.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全文检索信息
	public List<TzzJianjie> queryByTzzJianjieName(final String name) {
		 return null;
	}

	@Override
	public List<TzzJianjie> getindexList() { 
		return null; 
	}

}
