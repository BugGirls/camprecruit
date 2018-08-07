package com.jeefw.service.sys;
 
 

 
 
import java.util.List;

import com.jeefw.model.sys.TzzUser;

import core.service.Service;

/**
 * 用户的业务逻辑层的接口
 * @ 
 */
public interface TzzUserService extends Service<TzzUser> {
 
	TzzUser getByNickname(String nickname);
	//获取用户信息（将数据库查询出来的信息再处理，增加字段的中文意思）
	List<TzzUser> queryTzzUserCnList(List<TzzUser> resultList);
	
	List<TzzUser> getUserByids(List <Integer>ids);
	
}
