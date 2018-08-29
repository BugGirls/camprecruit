package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * ProductOffShelf  的参数类 
    */ 
public class ProductOffShelfParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8623375792147250273L;
	private String $like_productNo;
	private String $like_productName;
	private String $like_barCode;
	private String $like_brand;
	private String $like_bigCategoryName;
	private String $like_smallCategoryName;
	private String $like_storageLocation;
	
	public String get$like_productNo() {
		return $like_productNo;
	}
	public void set$like_productNo(String $like_productNo) {
		this.$like_productNo = $like_productNo;
	}
	public String get$like_storageLocation() {
		return $like_storageLocation;
	}
	public void set$like_storageLocation(String $like_storageLocation) {
		this.$like_storageLocation = $like_storageLocation;
	}
	public String get$like_brand() {
		return $like_brand;
	}
	public void set$like_brand(String $like_brand) {
		this.$like_brand = $like_brand;
	}
	public String get$like_barCode() {
		return $like_barCode;
	}
	public void set$like_barCode(String $like_barCode) {
		this.$like_barCode = $like_barCode;
	}
	
	public String get$like_bigCategoryName() {
		return $like_bigCategoryName;
	}
	public void set$like_bigCategoryName(String $like_bigCategoryName) {
		this.$like_bigCategoryName = $like_bigCategoryName;
	}
	public String get$like_smallCategoryName() {
		return $like_smallCategoryName;
	}
	public void set$like_smallCategoryName(String $like_smallCategoryName) {
		this.$like_smallCategoryName = $like_smallCategoryName;
	}
	public String get$like_productName() {
		return $like_productName;
	}
	public void set$like_productName(String $like_productName) {
		this.$like_productName = $like_productName;
	}
	

}

