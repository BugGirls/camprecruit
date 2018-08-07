package com.jeefw.dao.sys.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jeefw.dao.sys.MailDao;
import com.jeefw.model.sys.Mail;
import com.jeefw.model.sys.SysUser;

import core.dao.BaseDao;

/**
 * 邮件的数据持久层的实现类
 * @ 
 */
@Repository
public class MailDaoImpl extends BaseDao<Mail> implements MailDao {

	public MailDaoImpl() {
		super(Mail.class);
	}
	
	/**
	 * 分页查询
	 * 查询的页码，和每页的条数
	 */
	@Override
	public List<Mail> getpage(int pagenum, int maxtiaoshu) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		long id=((SysUser) session.getAttribute("SESSION_SYS_USER")).getId();
		Query query =  this.getSession().createQuery("from Mail where userId="+id+" ORDER by id desc");
		query.setFirstResult(pagenum);
		query.setMaxResults(maxtiaoshu);
		return query.list(); 
	}

}
