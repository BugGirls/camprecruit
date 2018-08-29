package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * ProductInfo  的参数类 
    */ 
public class ProductInfoParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8623375792147250273L;
	private String $like_no;
	private String $like_name;
	private String $like_barCode;
	private String $like_brand;
	private String $like_bigCategoryName;
	private String $like_smallCategoryName;
	
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
	public String get$like_name() {
		return $like_name;
	}
	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}
	public String get$like_no() {
		return $like_no;
	}
	public void set$like_no(String $like_no) {
		this.$like_no = $like_no;
	}


}

