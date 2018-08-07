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
import com.jeefw.model.sys.param.TzzUserEvaluationParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzUserEvaluation 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user_evaluation" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserEvaluation extends  TzzUserEvaluationParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;

	@Column(name = "product_id", length = 12 )
	private int productId;

	@Column(name = "content", length = 255 )
	private String content;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;
	
	

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TzzUserEvaluation(){	
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

	public void setProductId(int productId){
		this.productId=productId;
	}
	public int getProductId(){
		return productId;
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
		final TzzUserEvaluation other = (TzzUserEvaluation) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userId, other.userId) && Objects.equal(this.productId, other.productId) && Objects.equal(this.content, other.content);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.productId,this.content);
	}
}

