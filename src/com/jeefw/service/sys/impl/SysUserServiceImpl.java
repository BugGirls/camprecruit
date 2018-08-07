package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.AttachmentDao;
import com.jeefw.dao.sys.DepartmentDao;
import com.jeefw.dao.sys.SysUserDao;
import com.jeefw.model.sys.Attachment;
import com.jeefw.model.sys.SysUser;
import com.jeefw.service.sys.SysUserService;

import core.service.BaseService;

/**
 * 用户的业务逻辑层的实现
 * @ 
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService {

	private SysUserDao sysUserDao;
	@Resource
	private AttachmentDao attachmentDao;
	@Resource
	private DepartmentDao departmentDao;

	@Resource
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		this.dao = sysUserDao;
	}

	// 获取用户信息（将数据库查询出来的信息再处理，增加字段的中文意思）
	public List<SysUser> querySysUserCnList(List<SysUser> resultList) {
		Map<String,String> departmentMap = departmentDao.getDepartmentMap();
		
		List<SysUser> sysUserList = new ArrayList<SysUser>();
		for (SysUser entity : resultList) {
			SysUser sysUser = new SysUser();
			sysUser.setId(entity.getId());
			sysUser.setUserName(entity.getUserName());
			sysUser.setSex(entity.getSex());
			if (entity.getSex() == 1) {
				sysUser.setSexCn("男");
			} else if (entity.getSex() == 2) {
				sysUser.setSexCn("女");
			}
			sysUser.setEmail(entity.getEmail());
			sysUser.setPhone(entity.getPhone());
			sysUser.setBirthday(entity.getBirthday());
			sysUser.setDepartmentKey(entity.getDepartmentKey());
			if (StringUtils.isNotBlank(sysUser.getDepartmentKey())) {
//				Department department = departmentDao.getByProerties("departmentKey", sysUser.getDepartmentKey());
//				sysUser.setDepartmentValue(department.getDepartmentValue());
				sysUser.setDepartmentValue(departmentMap.get(sysUser.getDepartmentKey()));
			}
			sysUser.setPassword(entity.getPassword());
			sysUser.setRoleCn(sysUserDao.getRoleValueBySysUserId(entity.getId()));
			if (entity.getStatus() == true) {
				sysUser.setStatusCn("是");
			} else {
				sysUser.setStatusCn("否");
			}
			sysUser.setLastLoginTime(entity.getLastLoginTime());
			sysUser.setAllianceId(entity.getAllianceId());
			sysUserList.add(sysUser);
		}
		return sysUserList;
	}

	// 获取个人资料信息（将数据库查询出来的信息再处理，增加头像）
	public SysUser getSysUserWithAvatar(SysUser sysuser) {
		SysUser entity = new SysUser();
		entity.setId(sysuser.getId());
		entity.setUserName(sysuser.getUserName());
		entity.setSex(sysuser.getSex());
		entity.setEmail(sysuser.getEmail());
		entity.setPhone(sysuser.getPhone());
		entity.setBirthday(sysuser.getBirthday());
		entity.setPassword(sysuser.getPassword());
		entity.setStatus(sysuser.getStatus());
		entity.setLastLoginTime(sysuser.getLastLoginTime());
		Attachment attachment = attachmentDao.getByProerties(new String[] { "type", "typeId" }, new Object[] { (short) 1, sysuser.getId() });
		if (null != attachment) {
			entity.setFilePath(attachment.getFilePath());
		} else {
			entity.setFilePath("/static/assets/avatars/profile-pic.jpg");
		}
		return entity;
	}

	@Override
	public String getSysUserkeyWithOpenid(String openid) {
		return sysUserDao.getSysUserkeyWithOpenid(openid);
	}
 

}
