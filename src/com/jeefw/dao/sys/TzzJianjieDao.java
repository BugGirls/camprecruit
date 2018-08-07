package com.jeefw.dao.sys;

import java.util.List;
 


import com.jeefw.model.sys.TzzJianjie;

import core.dao.Dao;

/**
 * 信息发布的数据持久层的接口
 * @ 
 */
public interface TzzJianjieDao extends Dao<TzzJianjie> {

	// 生成信息的索引
	void indexingTzzJianjie();

	// 全文检索信息
	List<TzzJianjie> queryByTzzJianjieName(String name);
	List<TzzJianjie> getindexList();
}
