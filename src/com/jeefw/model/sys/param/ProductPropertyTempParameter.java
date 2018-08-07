package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 字典的参数类
 * @ 
 */
public class ProductPropertyTempParameter extends ExtJSBaseParameter {

	private String $eq_propertyKey;
	private String $like_propertyValue;
	private String $eq_parentPropertykey;
	public String get$eq_propertyKey() {
		return $eq_propertyKey;
	}
	public void set$eq_propertyKey(String $eq_propertyKey) {
		this.$eq_propertyKey = $eq_propertyKey;
	}
	public String get$like_propertyValue() {
		return $like_propertyValue;
	}
	public void set$like_propertyValue(String $like_propertyValue) {
		this.$like_propertyValue = $like_propertyValue;
	}
	public String get$eq_parentPropertykey() {
		return $eq_parentPropertykey;
	}
	public void set$eq_parentPropertykey(String $eq_parentPropertykey) {
		this.$eq_parentPropertykey = $eq_parentPropertykey;
	}
	

}
