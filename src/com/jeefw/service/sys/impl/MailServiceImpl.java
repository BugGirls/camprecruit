package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.border.EtchedBorder;

import org.springframework.stereotype.Service;

import com.jeefw.dao.sys.MailDao;
import com.jeefw.model.sys.Information;
import com.jeefw.model.sys.Mail;
import com.jeefw.model.sys.TzzNews;
import com.jeefw.service.sys.MailService;

import core.service.BaseService;
import core.util.HtmlUtils;

/**
 * 邮件的业务逻辑层的实现
 * @ 
 */
@Service
public class MailServiceImpl extends BaseService<Mail> implements MailService {

	private MailDao mailDao;

	@Resource
	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
		this.dao = mailDao;
	}

	// 获取信息，包括内容的HTML和过滤HTML两部分

		public List<Mail> queryEmailHTMLToList(List<Mail> resultList) {
			List<Mail> mailList = new ArrayList<Mail>();
			for (Mail entity : resultList) {
				Mail mail = new Mail();
				mail.setId(entity.getId());
				mail.setUserId(entity.getUserId()); 
				mail.setSubject(entity.getSubject());
				mail.setSender(entity.getSender());
				mail.setCreateTime(entity.getCreateTime());
				mail.setMessage(entity.getMessage()); 
				mail.setRecipient(entity.getRecipient());
				mail.setMessageNoHTML(HtmlUtils.htmltoText(entity.getMessage()));
				mailList.add(mail);
			}
			return mailList;
		}

		@Override
		public List<Mail> getmailpage(int pagenum, int maxtiaoshu) {
			pagenum=(pagenum-1)*maxtiaoshu;
			return mailDao.getpage(pagenum, maxtiaoshu);
		  
		}
}
