package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 回复消息设置的参数类
 * @ 
 */
public class ReplyMsgSetParameter extends ExtJSBaseParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2592767290724630271L;
	private String $eq_receiveMsgKey;
	private String $like_receiveMsgValue;
	public String get$eq_receiveMsgKey() {
		return $eq_receiveMsgKey;
	}
	public void set$eq_receiveMsgKey(String $eq_receiveMsgKey) {
		this.$eq_receiveMsgKey = $eq_receiveMsgKey;
	}
	public String get$like_receiveMsgValue() {
		return $like_receiveMsgValue;
	}
	public void set$like_receiveMsgValue(String $like_receiveMsgValue) {
		this.$like_receiveMsgValue = $like_receiveMsgValue;
	}


}
