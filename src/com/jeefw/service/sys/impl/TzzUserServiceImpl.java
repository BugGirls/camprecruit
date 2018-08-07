package com.jeefw.service.sys.impl;
   
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.SysUserDao;
import com.jeefw.dao.sys.TzzDictionaryDao;
import com.jeefw.dao.sys.TzzUserDao;
import com.jeefw.dao.sys.TzzUserSetvipDao;
import com.jeefw.model.sys.TzzUser;
import com.jeefw.service.sys.TzzUserService;

import core.service.BaseService; 

/**
 * 用户的业务逻辑层的实现
 * @ 
 */
@Service
public class TzzUserServiceImpl extends BaseService<TzzUser> implements TzzUserService {

	private TzzUserDao tzzUserDao; 
	@Resource
	private TzzUserSetvipDao tzzUserSetvipDao;
	@Resource
	private TzzDictionaryDao tzzDictionaryDao;
	@Resource
	private SysUserDao sysUserDao;
	
	@Resource
	public void setTzzUserDao(TzzUserDao tzzUserDao) {
		this.tzzUserDao = tzzUserDao;
		this.dao = tzzUserDao;
	}
	
	@Override
	public TzzUser getByNickname(String nickname) { 
		
		return tzzUserDao.getByProerties("nickname", nickname);
	}
 
	 //id password phone image nickname name email sex qq birthday createTime company level uType viptype totalAmout 
	// 获取用户信息（将数据库查询出来的信息再处理，增加字段的中文意思）
		public List<TzzUser> queryTzzUserCnList(List<TzzUser> resultList) {
			List<TzzUser> tzzUserList = new ArrayList<TzzUser>();
			for (TzzUser entity : resultList) {
				TzzUser tzzUser = new TzzUser();
				tzzUser.setId(entity.getId());
				tzzUser.setRealname(entity.getRealname());
				tzzUser.setUsername(entity.getUsername());
				if(null==entity.getSex())
					entity.setSex("0");
				tzzUser.setSex(entity.getSex());
				if(entity.getSex().equals("0")){
					tzzUser.setSexcn("保密");
				}else if (entity.getSex().equals("1")) {
					tzzUser.setSexcn("男");
				} else if (entity.getSex().equals("2")) {
					tzzUser.setSexcn("女");
				}
				
				tzzUser.setuType(entity.getUType());
				tzzUser.setEmail(entity.getEmail());
				tzzUser.setPhone(entity.getPhone());
				tzzUser.setQq(entity.getQq());
				tzzUser.setBirthday(entity.getBirthday()); 
				if(null!=entity.getFromUser())
					tzzUser.setUserName(sysUserDao.get(Long.parseLong(entity.getFromUser().toString())).getUserName());
				tzzUser.setCompany(entity.getCompany());  
			  	tzzUser.setTotalAmout(entity.getTotalAmout());
				tzzUser.setPassword(entity.getPassword());  
				tzzUser.setCreateTime(entity.getCreateTime());
				tzzUserList.add(tzzUser);
			}
			return tzzUserList;
		}

		@Override
		public List<TzzUser> getUserByids(List<Integer> ids) {
			// TODO Auto-generated method stub
			List<TzzUser> list = this.tzzUserDao.getUserByids(ids);
			return list;
		}

//		// 获取个人资料信息（将数据库查询出来的信息再处理，增加头像）
//		public SysUser getSysUserWithAvatar(SysUser sysuser) {
//			SysUser entity = new SysUser();
//			entity.setId(sysuser.getId());
//			entity.setUserName(sysuser.getUserName());
//			entity.setSex(sysuser.getSex());
//			entity.setEmail(sysuser.getEmail());
//			entity.setPhone(sysuser.getPhone());
//			entity.setBirthday(sysuser.getBirthday());
//			entity.setPassword(sysuser.getPassword());
//			entity.setStatus(sysuser.getStatus());
//			entity.setLastLoginTime(sysuser.getLastLoginTime());
//			Attachment attachment = attachmentDao.getByProerties(new String[] { "type", "typeId" }, new Object[] { (short) 1, sysuser.getId() });
//			if (null != attachment) {
//				entity.setFilePath(attachment.getFilePath());
//			} else {
//				entity.setFilePath("/static/assets/avatars/profile-pic.jpg");
//			}
//			return entity;
//		}

//		public String updateTzzUser(UpdateUserPwdRequestBean brb) {
//			// brb.setPassword(MD5.crypt(brb.getPassword()));
//			brb.setPassword(new Sha256Hash(brb.getPassword()).toHex());
//			sysUserDao.updateByProperties("userName", brb.getUsername(), "password", brb.getPassword());
//			return "success";
//		}

}
