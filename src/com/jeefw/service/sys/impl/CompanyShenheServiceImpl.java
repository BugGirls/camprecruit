package com.jeefw.service.sys.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.CompanyShenheDao;
import com.jeefw.dao.sys.SysUserDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.model.sys.CompanyShenhe;
import com.jeefw.service.sys.CompanyShenheService;

import core.service.BaseService;

/**
 * 用户的业务逻辑层的实现 @
 */
@Service
public class CompanyShenheServiceImpl extends BaseService<CompanyShenhe> implements CompanyShenheService {

	private CompanyShenheDao tzzUserDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	private SysUserDao sysUserDao;

	@Resource
	public void setCompanyShenheDao(CompanyShenheDao tzzUserDao) {
		this.tzzUserDao = tzzUserDao;
		this.dao = tzzUserDao;
	}

	@Override
	public List<CompanyShenhe> getUserByids(List<Integer> ids) {
		// TODO Auto-generated method stub
		List<CompanyShenhe> list = this.tzzUserDao.getUserByids(ids);
		return list;
	}

	// // 获取个人资料信息（将数据库查询出来的信息再处理，增加头像）
	// public SysUser getSysUserWithAvatar(SysUser sysuser) {
	// SysUser entity = new SysUser();
	// entity.setId(sysuser.getId());
	// entity.setUserName(sysuser.getUserName());
	// entity.setSex(sysuser.getSex());
	// entity.setEmail(sysuser.getEmail());
	// entity.setPhone(sysuser.getPhone());
	// entity.setBirthday(sysuser.getBirthday());
	// entity.setPassword(sysuser.getPassword());
	// entity.setStatus(sysuser.getStatus());
	// entity.setLastLoginTime(sysuser.getLastLoginTime());
	// Attachment attachment = attachmentDao.getByProerties(new String[] {
	// "type", "typeId" }, new Object[] { (short) 1, sysuser.getId() });
	// if (null != attachment) {
	// entity.setFilePath(attachment.getFilePath());
	// } else {
	// entity.setFilePath("/static/assets/avatars/profile-pic.jpg");
	// }
	// return entity;
	// }

	// public String updateCompanyShenhe(UpdateUserPwdRequestBean brb) {
	// // brb.setPassword(MD5.crypt(brb.getPassword()));
	// brb.setPassword(new Sha256Hash(brb.getPassword()).toHex());
	// sysUserDao.updateByProperties("userName", brb.getUsername(), "password",
	// brb.getPassword());
	// return "success";
	// }

}
