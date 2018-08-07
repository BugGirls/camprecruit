package com.jeefw.model.sys.param;



import java.util.Date;

import core.support.ExtJSBaseParameter; 

   /**
    * TzzDictionary  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzDictionaryParameter extends  ExtJSBaseParameter{


	

	private String $like_name;

	private String $eq_title;

	private String $eq_type;

	private String $eq_level;
	
	private Integer $eq_parentId;
	
	private String parentcn;
	
	public Integer get$eq_parentId() {
		return $eq_parentId;
	}

	public void set$eq_parentId(Integer $eq_parentId) {
		this.$eq_parentId = $eq_parentId;
	}

	public String get$like_name() {
		return $like_name;
	}

	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}

	public String get$eq_title() {
		return $eq_title;
	}

	public void set$eq_title(String $eq_title) {
		this.$eq_title = $eq_title;
	}

	public String get$eq_type() {
		return $eq_type;
	}

	public void set$eq_type(String $eq_type) {
		this.$eq_type = $eq_type;
	}

	public String getParentcn() {
		return parentcn;
	}

	public void setParentcn(String parentcn) {
		this.parentcn = parentcn;
	}

	public String get$eq_level() {
		return $eq_level;
	}

	public void set$eq_level(String $eq_level) {
		this.$eq_level = $eq_level;
	}

	




}

