package com.jeefw.model.sys.param;


  
import core.support.ExtJSBaseParameter; 

   /**
    * TzzUser  的参数类 
    * Thu Sep 17 18:18:08 CST 2015  tudou
    */ 
public class TzzUserParameter extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8967901744347798106L;

	private String $eq_phone;
	
	private String $like_phone;
  
	private String $eq_nickname;

	private String $eq_name;
	
	private String $like_name;
	 
	private String $eq_email;

	private String sexcn;

	private String $eq_qq;
  
	private String levelcn;
  
	private String viptypecn;
	
	private String phoneNick;
	
	private String allianceName;
	
	private String userName;
	
	private Integer $eq_userid;
	
	public String getPhoneNick() {
		return phoneNick;
	}

	public void setPhoneNick(String phoneNick) {
		this.phoneNick = phoneNick;
	}

	public String get$eq_phone() {
		return $eq_phone;
	}

	public void set$eq_phone(String $eq_phone) {
		this.$eq_phone = $eq_phone;
	}

	public String get$eq_nickname() {
		return $eq_nickname;
	}

	public void set$eq_nickname(String $eq_nickname) {
		this.$eq_nickname = $eq_nickname;
	}

	public String get$eq_name() {
		return $eq_name;
	}

	public void set$eq_name(String $eq_name) {
		this.$eq_name = $eq_name;
	}

	public String get$eq_email() {
		return $eq_email;
	}

	public void set$eq_email(String $eq_email) {
		this.$eq_email = $eq_email;
	}

	public String getSexcn() {
		return sexcn;
	}

	public void setSexcn(String sexcn) {
		this.sexcn = sexcn;
	}

	public String get$eq_qq() {
		return $eq_qq;
	}

	public void set$eq_qq(String $eq_qq) {
		this.$eq_qq = $eq_qq;
	}

	public String getLevelcn() {
		return levelcn;
	}

	public void setLevelcn(String levelcn) {
		this.levelcn = levelcn;
	}

	public String getViptypecn() {
		return viptypecn;
	}

	public void setViptypecn(String viptypecn) {
		this.viptypecn = viptypecn;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String get$like_phone() {
		return $like_phone;
	}

	public void set$like_phone(String $like_phone) {
		this.$like_phone = $like_phone;
	}

	public String get$like_name() {
		return $like_name;
	}

	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}

	public Integer get$eq_userid() {
		return $eq_userid;
	}

	public void set$eq_userid(Integer $eq_userid) {
		this.$eq_userid = $eq_userid;
	}
	 

	 
}

