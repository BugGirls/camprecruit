package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzShizhanPath  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzShizhanPathParameter extends  ExtJSBaseParameter{


	private String $title;

	private String $topImage;

	private String $image;

	private String $sketch;

	private String $content;

	private String $videoHref;

	private Date $createTime;


	public void set$title(String $title){
		this.$title=$title;
	}
	public String get$title(){
		return $title;
	}

	public void set$topImage(String $topImage){
		this.$topImage=$topImage;
	}
	public String get$topImage(){
		return $topImage;
	}

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

	public void set$content(String $content){
		this.$content=$content;
	}
	public String get$content(){
		return $content;
	}

	public void set$videoHref(String $videoHref){
		this.$videoHref=$videoHref;
	}
	public String get$videoHref(){
		return $videoHref;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

