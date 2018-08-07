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
import com.jeefw.model.sys.param.TzzOthersParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzOthers 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_others" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzOthers extends  TzzOthersParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "image", length = 255 )
	private String image;

	@Column(name = "sketch", length = 255 )
	private String sketch;

	@Column(name = "title", length = 255 )
	private String title;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "type", length = 1 )
	private String type;

	@Column(name = "create_time" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;


	public TzzOthers(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setImage(String image){
		this.image=image;
	}
	public String getImage(){
		return image;
	}

	public void setSketch(String sketch){
		this.sketch=sketch;
	}
	public String getSketch(){
		return sketch;
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

	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
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
		final TzzOthers other = (TzzOthers) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.image, other.image) && Objects.equal(this.sketch, other.sketch) && Objects.equal(this.title, other.title) && Objects.equal(this.content, other.content) && Objects.equal(this.type, other.type) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.image,this.sketch,this.title,this.content,this.type,this.createTime);
	}
}

