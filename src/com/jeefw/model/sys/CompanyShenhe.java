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
import com.jeefw.model.sys.param.TzzUserParameter;

import core.support.DateTimeSerializer;

   /**
    * CompanyShenhe 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="company_shenhe" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CompanyShenhe extends  TzzUserParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460688154108976071L;


	@Id
	@Column(name = "id")
	private Integer userid;
  
	@Column(name = "daima", length = 11 )
	private String daima;

	@Column(name = "faren", length = 64 )
	private String faren;

	@Column(name = "image", length = 256 )
	private String image;

	@Column(name = "shenheyijian", length = 32 )
	private String shenheyijian;
	
	@Column(name = "name", length = 32 )
	private String name;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;
	
	@Column(name = "shenhe_time")
	private String shenheTime;
	
	@Column(name = "stage", length = 2,columnDefinition="int default 0")
	private int stage;
	
	public CompanyShenhe(){	
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public void setImage(String image){
		this.image=image;
	}
	public String getImage(){
		return image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	 
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime(){
		return createTime;
	}


	public String getShenheTime() {
		return shenheTime;
	}

	public void setShenheTime(String shenheTime) {
		this.shenheTime = shenheTime;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public String getDaima() {
		return daima;
	}

	public void setDaima(String daima) {
		this.daima = daima;
	}

	public String getFaren() {
		return faren;
	}

	public void setFaren(String faren) {
		this.faren = faren;
	}

	public String getShenheyijian() {
		return shenheyijian;
	}

	public void setShenheyijian(String shenheyijian) {
		this.shenheyijian = shenheyijian;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CompanyShenhe other = (CompanyShenhe) obj;
		return  Objects.equal(this.userid, other.userid) &&
				Objects.equal(this.name, other.name) &&
				Objects.equal(this.image, other.image) &&
				Objects.equal(this.createTime, other.createTime);
	}
//	@Override
//	public String toString(){
//		String json = "{id:"+this.id+",realname:'"+this.realname+"',username:'"+this.username+"'}";
//		return json;
//	}
}

