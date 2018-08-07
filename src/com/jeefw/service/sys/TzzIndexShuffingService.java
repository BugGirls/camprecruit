package com.jeefw.service.sys;

import java.util.List;
 
import com.jeefw.model.sys.TzzIndexShuffing;

import core.service.Service;

/**
 * 活动轮播的业务逻辑层的接口
 * @ 
 */
public interface TzzIndexShuffingService extends Service<TzzIndexShuffing> {

	List<TzzIndexShuffing> queryTzzIndexShuffingWithSubList(List<TzzIndexShuffing> resultList);

	List<TzzIndexShuffing> queryTzzIndexShuffingCnList(List<TzzIndexShuffing> resultList);
 
}
