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

import com.jeefw.model.sys.param.SysUserParameter;

/**
 * 个人账号的实体类
 * @ 
 */
@Entity
@Table(name = "individual_user")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class IndividualUser extends SysUserParameter {

	private static final long serialVersionUID = 8046288864288310753L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "phone", length = 11)
	private String phone;
	
	@Column(name = "password", length = 64)
	private String password;
	
	@Column(name = "email", length = 32)
	private String email;
	
	@Column(name = "name", length = 16)
	private String name;
	
	@Column(name = "username", length = 20)
	private String username;
	
	@Column(name = "idcardno", length = 18)
	private String idcardno;
	
	@Column(name = "qq", length = 13)
	private String qq;
	
	@Column(name = "gender", length = 1)
	private String gender;
	
	@Column(name = "age", length = 3)
	private int age;
	
	@Column(name = "birthday")
	@Temporal(TemporalType.DATE) 
	private Date birthday;

	@Column(name = "edubackground", length = 16)
	private String edubackground;
	
	@Column(name = "workyears", length = 32)
	private String workyears;
	
	@Column(name = "currentstate", length = 16)
	private String currentstate;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;
	
	@Column(name = "last_login_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date lastloginTime;

	@Column(name = "image", length = 256)
	private String image;
	
	@Column(name = "selfassessment")
	private String selfassessment;

	@Column(name = "nation", length = 32)
	private String nation;
	
	@Column(name = "residence", length = 128)
	private String residence;
	
	@Column(name = "nativeplace", length = 128)
	private String nativeplace;
	
	@Column(name = "hobby", length = 256)
	private String hobby;
	
	@Column(name = "politicalstate", length = 32)
	private String politicalstate;
	
	public IndividualUser() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastloginTime() {
		return lastloginTime;
	}

	public void setLastloginTime(Date lastloginTime) {
		this.lastloginTime = lastloginTime;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWorkyears() {
		return workyears;
	}

	public void setWorkyears(String workyears) {
		this.workyears = workyears;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getPoliticalstate() {
		return politicalstate;
	}

	public void setPoliticalstate(String politicalstate) {
		this.politicalstate = politicalstate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
