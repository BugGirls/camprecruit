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
@Table(name ="tzz_message" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzMessage extends  TzzMessageParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "type", length = 2 )
	private int type;

	@Column(name = "title", length = 64 )
	private String title;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;


	public TzzMessage(){	
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

  
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzMessage other = (TzzMessage) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.type, other.type) && Objects.equal(this.title, other.title) && Objects.equal(this.content, other.content) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.type,this.title,this.content,this.createTime);
	}
}

