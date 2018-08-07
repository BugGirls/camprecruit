package com.jeefw.model.sys;



import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import javax.persistence.Table; 

import org.codehaus.jackson.annotate.JsonIgnoreProperties; 
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzShizhanBaikeParameter;

   /**
    * TzzShizhanBaike 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_shizhan_baike" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzShizhanBaike extends  TzzShizhanBaikeParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "title", length = 255 )
	private String title;

	@Column(name = "content", length = 255 )
	private String content;


	public TzzShizhanBaike(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
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

 

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzShizhanBaike other = (TzzShizhanBaike) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.title, other.title) && Objects.equal(this.content, other.content);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.title,this.content);
	}
}

