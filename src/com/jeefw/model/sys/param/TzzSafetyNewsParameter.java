package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * TzzSafetyNews  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzSafetyNewsParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8623375792147250283L;
	
	private String originName;
	private String $like_name;
	private String typeName; 
	
	private String reservitionCount; 
	
	public String get$like_name() {
		return $like_name;
	}
	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getReservitionCount() {
		return reservitionCount;
	}
	public void setReservitionCount(String reservitionCount) {
		this.reservitionCount = reservitionCount;
	}
	

}

