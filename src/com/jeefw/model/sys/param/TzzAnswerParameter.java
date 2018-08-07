package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzAnswer  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzAnswerParameter extends  ExtJSBaseParameter{

 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -830609721741536018L;

	private String username;
	
	private String zanhou;
	
	private String contentNoHTML;
	
	private String userimg;
 	
	private String time;
	private String $like_content;
	private int $eq_questionId;
	
	private String adoptcn;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContentNoHTML() {
		return contentNoHTML;
	}

	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}
	public String get$like_content() {
		return $like_content;
	}
	public void set$like_content(String $like_content) {
		this.$like_content = $like_content;
	}
	public String getAdoptcn() {
		return adoptcn;
	}
	public void setAdoptcn(String adoptcn) {
		this.adoptcn = adoptcn;
	}
	public int get$eq_questionId() {
		return $eq_questionId;
	}
	public void set$eq_questionId(int $eq_questionId) {
		this.$eq_questionId = $eq_questionId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getZanhou() {
		return zanhou;
	}
	public void setZanhou(String zanhou) {
		this.zanhou = zanhou;
	}
 


}

