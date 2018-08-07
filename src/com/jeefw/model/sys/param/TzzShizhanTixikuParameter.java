package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzShizhanTixiku  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzShizhanTixikuParameter extends  ExtJSBaseParameter{


	private int $pathId;

	private String $name;

	private String $title;

	private Date $createTime;


	public void set$pathId(int $pathId){
		this.$pathId=$pathId;
	}
	public int get$pathId(){
		return $pathId;
	}

	public void set$name(String $name){
		this.$name=$name;
	}
	public String get$name(){
		return $name;
	}

	public void set$title(String $title){
		this.$title=$title;
	}
	public String get$title(){
		return $title;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

