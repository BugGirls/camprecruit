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
import com.jeefw.model.sys.param.TzzDictionaryParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzDictionary 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_dictionary" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzDictionary extends  TzzDictionaryParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", length = 64 )
	private String name;

	@Column(name = "title", length = 255 )
	private String title;

	@Column(name = "parent_id", length = 12 )
	private Integer parentId;

	@Column(name = "type", length = 1 )
	private String type;

	@Column(name = "level", length = 5)
	private String level;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;


	public TzzDictionary(){	
	}

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}

	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}
	public Integer getParentId(){
		return parentId;
	}

	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}

	public void setLevel(String level){
		this.level=level;
	}
	public String getLevel(){
		return level;
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
		final TzzDictionary other = (TzzDictionary) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name) && Objects.equal(this.title, other.title) && Objects.equal(this.parentId, other.parentId) && Objects.equal(this.type, other.type) && Objects.equal(this.level, other.level) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.name,this.title,this.parentId,this.type,this.level,this.createTime);
	}
}

