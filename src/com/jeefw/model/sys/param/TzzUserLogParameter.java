package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzUserLog  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzUserLogParameter extends  ExtJSBaseParameter{


	private int $userId;

	private String $actions;

	private String $uri;

	private Date $createTime;


	public void set$userId(int $userId){
		this.$userId=$userId;
	}
	public int get$userId(){
		return $userId;
	}

	public void set$actions(String $actions){
		this.$actions=$actions;
	}
	public String get$actions(){
		return $actions;
	}

	public void set$uri(String $uri){
		this.$uri=$uri;
	}
	public String get$uri(){
		return $uri;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

