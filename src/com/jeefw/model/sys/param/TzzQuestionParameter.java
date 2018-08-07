package com.jeefw.model.sys.param;


 

import core.support.ExtJSBaseParameter; 

   /**
    * TzzQuestion  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzQuestionParameter extends  ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3694583237279015980L;

	private String $like_title;
	
	private String contentNoHTML;
	
	private String answernum;
	
	private String username;
	private String userimg;
	private String clicknumstr;
	
	private String familyname;
	
	private String essencecn;
	
	private String time;
	private String topcn;
	
	private String statecn;
 
	private String firstans;
	
	public String getFirstans() {
		return firstans;
	}
	public void setFirstans(String firstans) {
		this.firstans = firstans;
	}
	public String get$like_title() {
		return $like_title;
	}
	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}
	public String getContentNoHTML() {
		return contentNoHTML;
	}
	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFamilyname() {
		return familyname;
	}
	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}
	public String getEssencecn() {
		return essencecn;
	}
	public void setEssencecn(String essencecn) {
		this.essencecn = essencecn;
	}
	public String getTopcn() {
		return topcn;
	}
	public void setTopcn(String topcn) {
		this.topcn = topcn;
	}
	public String getStatecn() {
		return statecn;
	}
	public void setStatecn(String statecn) {
		this.statecn = statecn;
	}
	public String getAnswernum() {
		return answernum;
	}
	public void setAnswernum(String answernum) {
		this.answernum = answernum;
	}
	public String getClicknumstr() {
		return clicknumstr;
	}
	public void setClicknumstr(String clicknumstr) {
		this.clicknumstr = clicknumstr;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
 
 

}

