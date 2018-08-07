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
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;

import core.support.DateTimeSerializer;
import core.support.ExtJSBaseParameter;

   /**
    * TzzSafetyNews 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_activityarea" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzActivityArea extends  ExtJSBaseParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = 7600663841191137750L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 60 )
	private String name;

	@Column(name = "brief", length = 255 )
	private String brief;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "creattime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date creattime;

	@Column(name = "creater", length = 255 )
	private String creater;

	@Column(name = "img", length = 255 )
	private String img;
	
	@Column(name = "ispublish", length = 1 )
	private int ispublish;

	
	public TzzActivityArea(){	
	}
	
	public TzzActivityArea(String name,String brief,String content,int ispublish,String img){	
		this.name = name;
		this.brief = brief;
		this.content = content;
		this.img= img;
		this.ispublish = ispublish;
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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getCreattime() {
		return creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public int getIspublish() {
		return ispublish;
	}

	public void setIspublish(int ispublish) {
		this.ispublish = ispublish;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzActivityArea other = (TzzActivityArea) obj;
		return Objects.equal(this.id, other.id)  && Objects.equal(this.name, other.name) && Objects.equal(this.brief, other.brief) && Objects.equal(this.content, other.content);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.name,this.brief,this.content);
	}
}

