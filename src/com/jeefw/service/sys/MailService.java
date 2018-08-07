package com.jeefw.service.sys;

import java.util.List;
 

import com.jeefw.model.sys.Mail; 

import core.service.Service;

/**
 * 邮件的业务逻辑层的接口
 * @ 
 */
public interface MailService extends Service<Mail> {
	 List<Mail> queryEmailHTMLToList(List<Mail> resultList); 

	 List<Mail> getmailpage( int pagenum, int maxtiaoshu);
}
