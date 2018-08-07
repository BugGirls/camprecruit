package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 邮件的参数类
 * @ 
 */
public class MailParameter extends ExtJSBaseParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4679821846147118764L;
	private String $like_subject;
	private String messageNoHTML;
	
	public String get$like_subject() {
		return $like_subject;
	}

	public void set$like_subject(String $like_subject) {
		this.$like_subject = $like_subject;
	}

	public String getMessageNoHTML() {
		return messageNoHTML;
	}

	public void setMessageNoHTML(String messageNoHTML) {
		this.messageNoHTML = messageNoHTML;
	}

}
