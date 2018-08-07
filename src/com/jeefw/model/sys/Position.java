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
 * 职位的实体类
 * @ 
 */
@Entity
@Table(name = "position")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Position extends RoleParameter {

	private static final long serialVersionUID = 8046288864288310753L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "name", length = 128)
	private String name;
	
	@Column(name = "type", length = 11)
	private int type;
	
	@Column(name = "department", length = 128)
	private String department;
	
	@Column(name = "nature", length = 1)
	private int nature;

	@Column(name = "salaryfrom", length = 9)
	private int salaryfrom;
	
	@Column(name = "salaryto", length = 9)
	private int salaryto;
	
	@Column(name = "workcity", length = 32)
	private String workcity;

	@Column(name = "experience", length = 4)
	private int experience;
	
	@Column(name = "edubackground", length = 4)
	private int edubackground;
	
	@Column(name = "brief")
	private String brief;
	
	@Column(name = "positionDetail")
	private String positionDetail;
	
	@Column(name = "skills", length=512)
	private String skills;
	
	@Column(name = "workplace", length=128)
	private String workplace;
	
	@Column(name = "email", length=64)
	private String email;
	
	@Column(name = "forwardEmail", length=64)
	private String forwardEmail;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;
	
	@Column(name = "creator", length = 11)
	private int creator;
	
	@Column(name = "companyid", length = 11)
	private int companyid;
	
	@Column(name = "status", length = 1)
	private int status;
	
	public Position() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getNature() {
		return nature;
	}

	public void setNature(int nature) {
		this.nature = nature;
	}

	public int getSalaryfrom() {
		return salaryfrom;
	}

	public void setSalaryfrom(int salaryfrom) {
		this.salaryfrom = salaryfrom;
	}

	public int getSalaryto() {
		return salaryto;
	}

	public void setSalaryto(int salaryto) {
		this.salaryto = salaryto;
	}

	public String getWorkcity() {
		return workcity;
	}

	public void setWorkcity(String workcity) {
		this.workcity = workcity;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getEdubackground() {
		return edubackground;
	}

	public void setEdubackground(int edubackground) {
		this.edubackground = edubackground;
	}
	
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getPositionDetail() {
		return positionDetail;
	}

	public void setPositionDetail(String positionDetail) {
		this.positionDetail = positionDetail;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getForwardEmail() {
		return forwardEmail;
	}

	public void setForwardEmail(String forwardEmail) {
		this.forwardEmail = forwardEmail;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
