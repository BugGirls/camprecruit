package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzShizhanBaike  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzShizhanBaikeParameter extends  ExtJSBaseParameter{


	private String $title;

	private String $content;


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

}

