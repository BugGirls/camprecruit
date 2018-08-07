package com.jeefw.dao.sys;

import java.util.List;

import com.jeefw.model.sys.Mail; 

import core.dao.Dao;

/**
 * 邮件的数据持久层的接口
 * @ 
 */
public interface MailDao extends Dao<Mail> {
	public List<Mail> getpage(int pagenum, int maxtiaoshu);
}
