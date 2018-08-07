package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 字典的参数类
 * @ 
 */
public class DictParameter extends ExtJSBaseParameter {

	private String $eq_dictKey;
	private String $like_dictValue;
	private String $eq_parentDictkey;

	public String get$eq_dictKey() {
		return $eq_dictKey;
	}

	public void set$eq_dictKey(String $eq_dictKey) {
		this.$eq_dictKey = $eq_dictKey;
	}

	public String get$like_dictValue() {
		return $like_dictValue;
	}

	public void set$like_dictValue(String $like_dictValue) {
		this.$like_dictValue = $like_dictValue;
	}

	public String get$eq_parentDictkey() {
		return $eq_parentDictkey;
	}

	public void set$eq_parentDictkey(String $eq_parentDictkey) {
		this.$eq_parentDictkey = $eq_parentDictkey;
	}

}
