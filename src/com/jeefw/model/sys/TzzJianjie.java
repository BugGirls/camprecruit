package com.jeefw.model.sys;


import java.util.Date;

import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import javax.persistence.Table; 
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties; 
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzJianjieParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzJianjie 实体类
    * 2015/10/24 10:04:07  tudou
    */ 
@Entity
@Table(name ="tzz_jianjie" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzJianjie extends  TzzJianjieParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Field(index = Index.YES, analyzer = @Analyzer(impl = SmartChineseAnalyzer.class), store = Store.YES)
	@Column(name = "image",columnDefinition = "LONGTEXT") 
	private String image;
	@Field(index = Index.YES, analyzer = @Analyzer(impl = SmartChineseAnalyzer.class), store = Store.YES)
	@Column(name = "content",columnDefinition = "LONGTEXT") 
	private String content;

	@Column(name = "refresh_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date refreshTime;  


	public TzzJianjie(){	
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

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
 
 
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzJianjie other = (TzzJianjie) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.image, other.image) && Objects.equal(this.content, other.content) && Objects.equal(this.refreshTime, other.refreshTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.image,this.content,this.refreshTime);
	}
}

