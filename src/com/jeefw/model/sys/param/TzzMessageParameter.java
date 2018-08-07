package com.jeefw.model.sys.param;


 

import core.support.ExtJSBaseParameter; 

   /**
    * TzzMessage  的参数类 
    * 2015/12/07 10:19:43  tudou
    */ 
public class TzzMessageParameter extends  ExtJSBaseParameter{

 
	private String $title;

	private String $content; 
	
	private String typecn;
	
	private String $like_title;
	
	private String contentNoHTML;
 
	public void set$title(String $title){
		this.$title=$title;
	}
	public String get$title(){
		return $title;
	}

	public void set$content(String $content){
		this.$content=$content;
	}
	public String get$content(){
		return $content;
	}
	public String getContentNoHTML() {
		return contentNoHTML;
	}
	public void setContentNoHTML(String contentNoHTML) {
		this.contentNoHTML = contentNoHTML;
	}
	public String get$like_title() {
		return $like_title;
	}
	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}
	public String getTypecn() {
		return typecn;
	}
	public void setTypecn(String typecn) {
		this.typecn = typecn;
	}
 

}

