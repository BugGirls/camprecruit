package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 信息发布的参数类
 * @ 
 */
public class InformationParameter extends ExtJSBaseParameter {

	private String $like_title;
	private String $like_author;
	private String contentNoHTML;
	private String subtitle;

	public String get$like_author() {
		return $like_author;
	}

	public void set$like_author(String $like_author) {
		this.$like_author = $like_author;
	}

	public String get$like_title() {
		return $like_title;
	}

	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContentNoHTML() {
		return contentNoHTML;
	}

	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}

}
