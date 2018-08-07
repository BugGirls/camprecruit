package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzUserUpload  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzUserUploadParameter extends  ExtJSBaseParameter{


	private int $userId;

	private String $file;

	private int $familyId;

	private String $name;

	private String $detail;

	private String $state;

	private String $result;

	private Date $createTime;


	public void set$userId(int $userId){
		this.$userId=$userId;
	}
	public int get$userId(){
		return $userId;
	}

	public void set$file(String $file){
		this.$file=$file;
	}
	public String get$file(){
		return $file;
	}

	public void set$familyId(int $familyId){
		this.$familyId=$familyId;
	}
	public int get$familyId(){
		return $familyId;
	}

	public void set$name(String $name){
		this.$name=$name;
	}
	public String get$name(){
		return $name;
	}

	public void set$detail(String $detail){
		this.$detail=$detail;
	}
	public String get$detail(){
		return $detail;
	}

	public void set$state(String $state){
		this.$state=$state;
	}
	public String get$state(){
		return $state;
	}

	public void set$result(String $result){
		this.$result=$result;
	}
	public String get$result(){
		return $result;
	}

	public void set$createTime(Date $createTime){
		this.$createTime=$createTime;
	}
	public Date get$createTime(){
		return $createTime;
	}

}

