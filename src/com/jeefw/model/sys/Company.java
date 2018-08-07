package com.jeefw.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jeefw.model.sys.param.RoleParameter;

/**
 * 公司的实体类
 * @ 
 */
@Entity
@Table(name = "company")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Company extends RoleParameter {

	private static final long serialVersionUID = 8046288864288310753L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "shortname", length = 32)
	private String shortname;
	
	@Column(name = "fullname", length = 128)
	private String fullname;
	
	@Column(name = "logo", length = 128)
	private String logo;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "city", length = 32)
	private String city;
	
	@Column(name = "email", length = 32)
	private String email;

	@Column(name = "contact", length = 16)
	private String contact;
	
	@Column(name = "address", length = 128)
	private String address;
	
	@Column(name = "scale", length = 2)
	private int scale;
	
	@Column(name = "stage", length = 2)
	private int stage;

	@Column(name = "field", length = 64)
	private String field;
	
	@Column(name = "founder", length = 32)
	private String founder;
	
	@Column(name = "website", length=128)
	private String website;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;
	
	@Column(name = "introduction")
	private String introduction;
	
	public Company() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
