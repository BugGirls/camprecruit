package com.jeefw.model.sys.param;
 

import core.support.ExtJSBaseParameter; 

   /**
    * TzzSms  的参数类 
    * 2015/10/12 10:30:22  tudou
    */ 
public class TzzSmsParameter extends  ExtJSBaseParameter{

   
	private String $content;
	private String $like_content;
	private String $eq_phone;
 

	 
	public String get$like_content() {
		return $like_content;
	}
	public void set$like_content(String $like_content) {
		this.$like_content = $like_content;
	}
	public String get$eq_phone() {
		return $eq_phone;
	}
	public void set$eq_phone(String $eq_phone) {
		this.$eq_phone = $eq_phone;
	}
	public void set$content(String $content){
		this.$content=$content;
	}
	public String get$content(){
		return $content;
	} 

}

