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
import com.jeefw.model.sys.param.TzzNewsParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzNews 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_news" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzNews extends  TzzNewsParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = -2098355394231783211L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;

	@Column(name = "title", length = 255 )
	private String title;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "create_time" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;


	@Column(name = "state", length = 1 )
	private String state;
	
	
	 
	public TzzNews(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setUserId(int userId){
		this.userId=userId;
	}
	public int getUserId(){
		return userId;
	}

	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
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
 
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzNews other = (TzzNews) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userId, other.userId) && Objects.equal(this.title, other.title) && Objects.equal(this.content, other.content) && Objects.equal(this.state, other.state) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.title,this.content,this.state,this.createTime);
	}
}

