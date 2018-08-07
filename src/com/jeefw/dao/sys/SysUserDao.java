package com.jeefw.dao.sys;

import com.jeefw.model.sys.SysUser;

import core.dao.Dao;

/**
 * 用户的数据持久层的接口
 * @ 
 */
public interface SysUserDao extends Dao<SysUser> {

	String getRoleValueBySysUserId(Long sysUserId);

	String getSysUserkeyWithOpenid(String openid);

}
