package com.jeefw.service.sys;

import java.util.List;
 
import com.jeefw.model.sys.TzzJianjie;

import core.service.Service;

/**
 * 信息发布的业务逻辑层的接口
 * @ 
 */
public interface TzzJianjieService extends Service<TzzJianjie> {

	// 获取信息，包括内容的HTML和过滤HTML两部分
	List<TzzJianjie> queryTzzJianjieHTMLList(List<TzzJianjie> resultList);

	// 生成信息的索引
	void indexingtzzJianjie();

	// 全文检索信息
	List<TzzJianjie> queryByTzzJianjieName(String name);
	
	List<TzzJianjie> getindexlistTzzJianjies();
	
}
