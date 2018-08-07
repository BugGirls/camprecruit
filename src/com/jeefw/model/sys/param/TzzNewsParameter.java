package com.jeefw.model.sys.param;


 

import core.support.ExtJSBaseParameter; 

   /**
    * TzzNews  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzNewsParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8399541236038092074L;

	private Integer $userId;

	private String $title;

	private String $content;
	
	private String $state;
  
	private String userame;


	public String getUserame() {
		return userame;
	}
	public Integer get$userId() {
		return $userId;
	}
	public void set$userId(Integer $userId) {
		this.$userId = $userId;
	}
	public void setUserame(String userame) {
		this.userame = userame;
	} 
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
	
	public String get$state() {
		return $state;
	}
	public void set$state(String $state) {
		this.$state = $state;
	}
 
}

