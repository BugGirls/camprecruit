package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzOthers  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzOthersParameter extends  ExtJSBaseParameter{


	private String $image;

	private String $sketch;

	private String $title;

	private String $content;

	private String $type;

	private Date $createTime;


	public void set$image(String $image){
		this.$image=$image;
	}
	public String get$image(){
		return $image;
	}

	public void set$sketch(String $sketch){
		this.$sketch=$sketch;
	}
	public String get$sketch(){
		return $sketch;
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

	public void set$type(String $type){
		this.$type=$type;
	}
	public String get$type(){
		return $type;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

