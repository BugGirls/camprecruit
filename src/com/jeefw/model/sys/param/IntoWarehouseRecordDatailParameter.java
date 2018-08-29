package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * ProductInfo  的参数类 
    */ 
public class IntoWarehouseRecordDatailParameter extends  ExtJSBaseParameter{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String $like_productNo;
	private String $like_productName;
	private String $like_storageLocation;
	public String get$like_productNo() {
		return $like_productNo;
	}
	public void set$like_productNo(String $like_productNo) {
		this.$like_productNo = $like_productNo;
	}
	public String get$like_productName() {
		return $like_productName;
	}
	public void set$like_productName(String $like_productName) {
		this.$like_productName = $like_productName;
	}
	public String get$like_storageLocation() {
		return $like_storageLocation;
	}
	public void set$like_storageLocation(String $like_storageLocation) {
		this.$like_storageLocation = $like_storageLocation;
	}
	
}

