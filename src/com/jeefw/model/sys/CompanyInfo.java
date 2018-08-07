package com.jeefw.model.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jeefw.model.sys.param.TzzUserParameter;

   /**
    * CompanyInfo 实体类
    */ 
@Entity
@Table(name ="company_info" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CompanyInfo extends  TzzUserParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460688154108976071L;


	@Id
	@Column(name = "id")
	private int userid;
  
	@Column(name = "full_name", length = 64 )
	private String fullname;

	@Column(name = "name", length = 32 )
	private String name;

	@Column(name = "image", length = 256 )
	private String image;

	@Column(name = "phone", length = 11 )
	private String phone;
	
	@Column(name = "website", length = 128 )
	private String website;
	
	@Column(name = "sheng_shi")
	private String shengshi;
	
	@Column(name = "sheng")
	private String sheng;
	
	@Column(name = "shi")
	private String shi;
	
	@Column(name = "hangye")
	private String hangye;
	
	@Column(name = "hangye_value")
	private String hangyevalue;
	
	@Column(name = "guimo")
	private String guimo;
	
	@Column(name = "guimo_value")
	private String guimovalue;
	
	@Column(name = "jieduan")
	private String jieduan;
	
	@Column(name = "jieduan_value")
	private String jieduanvalue;
	
	@Column(name = "brief")
	private String brief;
	
	@Column(name = "labels")
	private String labels;
	
	@Column(name = "profile")
	private String profile;
	
	public CompanyInfo(){	
	}
	
	public CompanyInfo(int userid,String fullname,String name,String image,String phone,
			String website,String shengshi,String sheng,String shi,String hangye,String hangyevalue,
			String guimo,String guimovalue,String jieduan,String jieduanvalue,String brief){
		this.userid = userid;
		this.fullname = fullname;
		this.name = name;
		this.image = image;
		this.phone = phone;
		this.website = website;
		this.shengshi = shengshi;
		this.sheng = sheng ;
		this.shi = shi;
		this.hangye = hangye;
		this.hangyevalue = hangyevalue; 
		this.guimo = guimo;
		this.guimovalue = guimovalue;
		this.jieduan = jieduan;
		this.jieduanvalue = jieduanvalue;
		this.brief = brief;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getShengshi() {
		return shengshi;
	}

	public void setShengshi(String shengshi) {
		this.shengshi = shengshi;
	}

	public String getSheng() {
		return sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getHangye() {
		return hangye;
	}

	public void setHangye(String hangye) {
		this.hangye = hangye;
	}

	public String getHangyevalue() {
		return hangyevalue;
	}

	public void setHangyevalue(String hangyevalue) {
		this.hangyevalue = hangyevalue;
	}

	public String getGuimo() {
		return guimo;
	}

	public void setGuimo(String guimo) {
		this.guimo = guimo;
	}

	public String getGuimovalue() {
		return guimovalue;
	}

	public void setGuimovalue(String guimovalue) {
		this.guimovalue = guimovalue;
	}

	public String getJieduan() {
		return jieduan;
	}

	public void setJieduan(String jieduan) {
		this.jieduan = jieduan;
	}

	public String getJieduanvalue() {
		return jieduanvalue;
	}

	public void setJieduanvalue(String jieduanvalue) {
		this.jieduanvalue = jieduanvalue;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	

//	@Override
//	public String toString(){
//		String json = "{id:"+this.id+",realname:'"+this.realname+"',username:'"+this.username+"'}";
//		return json;
//	}
}

