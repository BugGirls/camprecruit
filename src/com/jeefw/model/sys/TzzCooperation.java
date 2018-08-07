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
import com.jeefw.model.sys.param.TzzMessageParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzMessage 实体类
    * 2015/12/07 10:19:43  tudou
    */ 
@Entity
@Table(name ="tzz_cooperation" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzCooperation extends  TzzMessageParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = 6468925597771356811L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "type", length = 1 )
	private int type;

	@Column(name = "name", length = 64 )
	private String name;
	
	@Column(name = "sex", length = 1 )
	private int sex;

	@Column(name = "phone", length = 15 )
	private String phone;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;

	@Column(name = "openid", length = 64 )
	private String openid;

	public TzzCooperation(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

 
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getCreateTime(){
		return createTime;
	}
  
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzCooperation other = (TzzCooperation) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.type, other.type) && Objects.equal(this.name, other.name) && Objects.equal(this.content, other.content) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.type,this.name,this.content,this.createTime);
	}
}

