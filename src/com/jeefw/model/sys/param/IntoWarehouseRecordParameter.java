package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * IntoWarehouseRecord  的参数类 
    */ 
public class IntoWarehouseRecordParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8623375792147250273L;
	
	private String $like_no;
	private String $like_createtime;
	private String $like_creater;
	private String $like_title;
	private String $like_supplier;
	private Integer $eq_allianceId;
	
	public Integer get$eq_allianceId() {
		return $eq_allianceId;
	}
	public void set$eq_allianceId(Integer $eq_allianceId) {
		this.$eq_allianceId = $eq_allianceId;
	}
	public String get$like_no() {
		return $like_no;
	}
	public void set$like_no(String $like_no) {
		this.$like_no = $like_no;
	}
	public String get$like_createtime() {
		return $like_createtime;
	}
	public void set$like_createtime(String $like_createtime) {
		this.$like_createtime = $like_createtime;
	}
	public String get$like_creater() {
		return $like_creater;
	}
	public void set$like_creater(String $like_creater) {
		this.$like_creater = $like_creater;
	}
	public String get$like_title() {
		return $like_title;
	}
	public void set$like_title(String $like_title) {
		this.$like_title = $like_title;
	}
	public String get$like_supplier() {
		return $like_supplier;
	}
	public void set$like_supplier(String $like_supplier) {
		this.$like_supplier = $like_supplier;
	}
	

}

