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
 * 简历项目经验的实体类
 * @ 
 */
@Entity
@Table(name = "resume_project_experience")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ResumeProjectExperience extends RoleParameter {

	private static final long serialVersionUID = 8046288864288310753L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "resumeid", length = 11)
	private int resumeid;
	
	@Column(name = "project", length = 64)
	private String project;
	
	@Column(name = "duty", length = 32)
	private String duty;
	
	@Column(name = "fromyear", length = 4)
	private String fromyear;
	
	@Column(name = "frommonth", length = 2)
	private String frommonth;
	
	@Column(name = "toyear", length = 4)
	private String toyear;
	
	@Column(name = "tomonth", length = 4)
	private String tomonth;
	
	@Column(name = "projectdesc")
	private String projectdesc;
	
	public ResumeProjectExperience() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResumeid() {
		return resumeid;
	}

	public void setResumeid(int resumeid) {
		this.resumeid = resumeid;
	}

	public String getFromyear() {
		return fromyear;
	}

	public void setFromyear(String fromyear) {
		this.fromyear = fromyear;
	}

	public String getFrommonth() {
		return frommonth;
	}

	public void setFrommonth(String frommonth) {
		this.frommonth = frommonth;
	}

	public String getToyear() {
		return toyear;
	}

	public void setToyear(String toyear) {
		this.toyear = toyear;
	}

	public String getTomonth() {
		return tomonth;
	}

	public void setTomonth(String tomonth) {
		this.tomonth = tomonth;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getProjectdesc() {
		return projectdesc;
	}

	public void setProjectdesc(String projectdesc) {
		this.projectdesc = projectdesc;
	}

}
