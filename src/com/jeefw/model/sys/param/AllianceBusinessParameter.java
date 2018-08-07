package com.jeefw.model.sys.param;



import core.support.ExtJSBaseParameter; 

   /**
    * AllianceBusinessParameter  的参数类 
    * 
    */ 
public class AllianceBusinessParameter extends  ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350756717801781368L;
	
	private String $like_name;
	private Integer $eq_id;
	private String $like_typeValue;
	private String $like_brief;
	private String $like_industryValue;
	private String $like_address;
	private String $like_contacts;
	private String $like_contacts_phone;
	
	public String get$like_name() {
		return $like_name;
	}
	
	public String get$like_typeValue() {
		return $like_typeValue;
	}

	public void set$like_typeValue(String $like_typeValue) {
		this.$like_typeValue = $like_typeValue;
	}

	public String get$like_brief() {
		return $like_brief;
	}

	public void set$like_brief(String $like_brief) {
		this.$like_brief = $like_brief;
	}

	public String get$like_industryValue() {
		return $like_industryValue;
	}

	public void set$like_industryValue(String $like_industryValue) {
		this.$like_industryValue = $like_industryValue;
	}

	public String get$like_address() {
		return $like_address;
	}

	public void set$like_address(String $like_address) {
		this.$like_address = $like_address;
	}

	public String get$like_contacts() {
		return $like_contacts;
	}

	public void set$like_contacts(String $like_contacts) {
		this.$like_contacts = $like_contacts;
	}

	public String get$like_contacts_phone() {
		return $like_contacts_phone;
	}

	public void set$like_contacts_phone(String $like_contacts_phone) {
		this.$like_contacts_phone = $like_contacts_phone;
	}

	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}

	public Integer get$eq_id() {
		return $eq_id;
	}

	public void set$eq_id(Integer $eq_id) {
		this.$eq_id = $eq_id;
	}


}

