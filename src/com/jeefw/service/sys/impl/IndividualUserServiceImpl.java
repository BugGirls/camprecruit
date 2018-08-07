package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.IndividualUserDao;
import com.jeefw.model.sys.IndividualUser;
import com.jeefw.model.sys.SysUser;
import com.jeefw.service.sys.IndividualUserService;

import core.service.BaseService;

/**
 * 个人用户的业务逻辑层的实现
 * @ 
 */
@Service
public class IndividualUserServiceImpl extends BaseService<IndividualUser> implements IndividualUserService {

	private IndividualUserDao individualUserDao;

	@Resource
	public void setIndividualUserDao(IndividualUserDao individualDao) {
		this.individualUserDao = individualDao;
		this.dao = individualDao;
	}

	@Override
	public List<IndividualUser> queryIndividualUserCnList(List<IndividualUser> resultList) {
		//Map<String,String> departmentMap = departmentDao.getDepartmentMap();
		
		List<IndividualUser> individualUserList = new ArrayList<IndividualUser>();
		for (IndividualUser entity : resultList) {
//			IndividualUser sysUser = entity;
//			if (entity.getGender() == "1") {
//				sysUser.setSexn("男");
//			} else if (entity.getGender() == "2") {
//				sysUser.setSexn("女");
//			} else {
//				sysUser.setSexn("未知");
//			}
		}
		return individualUserList;
	}

}
