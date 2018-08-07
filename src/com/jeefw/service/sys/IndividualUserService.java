package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.IndividualUser;
import core.service.Service;

/**
 * 个人用户的业务逻辑层的接口
 * @ 
 */
public interface IndividualUserService extends Service<IndividualUser> {

	// 获取用户信息（将数据库查询出来的信息再处理，增加字段的中文意思）
	List<IndividualUser> queryIndividualUserCnList(List<IndividualUser> resultList);

}
