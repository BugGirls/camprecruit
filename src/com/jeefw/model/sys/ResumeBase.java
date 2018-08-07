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
 * 简历基本信息的实体类
 * @ 
 */
@Entity
@Table(name = "resume_base")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ResumeBase extends RoleParameter {

	private static final long serialVersionUID = 8046288864288310753L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "userid", length = 11)
	private int userid;
	
	@Column(name = "phone", length = 11)
	private String phone;
	
	@Column(name = "email", length = 32)
	private String email;
	
	@Column(name = "name", length = 16)
	private String name;
	
	@Column(name = "gender", length = 1)
	private int gender;
	
	@Column(name = "age", length = 3)
	private int age;

	@Column(name = "edubackground", length = 16)
	private String edubackground;
	
	@Column(name = "experience", length = 16)
	private String experience;
	
	@Column(name = "currentstate", length = 16)
	private String currentstate;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;
	
	@Column(name = "modifytime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date modifytime;

	@Column(name = "image", length = 128)
	private String image;
	
	@Column(name = "resumename", length = 128)
	private String resumename;
	
	@Column(name = "residence", length = 128)
	private String residence;
	
	@Column(name = "selfassessment")
	private String selfassessment;
	
	@Column(name = "integritydegree", length = 3)
	private int integritydegree;
	
	@Column(name = "isdefault", length = 1)
	private int isdefault;
	
	public ResumeBase() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getEdubackground() {
		return edubackground;
	}

	public void setEdubackground(String edubackground) {
		this.edubackground = edubackground;
	}

	public String getCurrentstate() {
		return currentstate;
	}

	public void setCurrentstate(String currentstate) {
		this.currentstate = currentstate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSelfassessment() {
		return selfassessment;
	}

	public void setSelfassessment(String selfassessment) {
		this.selfassessment = selfassessment;
	}

	public String getResumename() {
		return resumename;
	}

	public void setResumename(String resumename) {
		this.resumename = resumename;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public int getIntegritydegree() {
		return integritydegree;
	}

	public void setIntegritydegree(int integritydegree) {
		this.integritydegree = integritydegree;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

}
