package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 字典的参数类
 * @ 
 */
public class ProductInfoTypeParameter extends ExtJSBaseParameter {

	private String $eq_typeKey;
	private String $like_typeValue;
	private String $eq_parentTypekey;
	public String get$eq_typeKey() {
		return $eq_typeKey;
	}
	public void set$eq_typeKey(String $eq_typeKey) {
		this.$eq_typeKey = $eq_typeKey;
	}
	public String get$like_typeValue() {
		return $like_typeValue;
	}
	public void set$like_typeValue(String $like_typeValue) {
		this.$like_typeValue = $like_typeValue;
	}
	public String get$eq_parentTypekey() {
		return $eq_parentTypekey;
	}
	public void set$eq_parentTypekey(String $eq_parentTypekey) {
		this.$eq_parentTypekey = $eq_parentTypekey;
	}

	

}
