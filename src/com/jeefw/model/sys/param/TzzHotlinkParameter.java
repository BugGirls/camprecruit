package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzHotlink  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzHotlinkParameter extends  ExtJSBaseParameter{


	private String $image;

	private String $title;

	private String $href;

	private String $sort;

	private Date $createTime;


	public void set$image(String $image){
		this.$image=$image;
	}
	public String get$image(){
		return $image;
	}

	public void set$title(String $title){
		this.$title=$title;
	}
	public String get$title(){
		return $title;
	}

	public void set$href(String $href){
		this.$href=$href;
	}
	public String get$href(){
		return $href;
	}

	public void set$sort(String $sort){
		this.$sort=$sort;
	}
	public String get$sort(){
		return $sort;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

