package com.jeefw.model.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;

   /**
    * TzzUserRecommend 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user_recommend" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserRecommend {


/**
	 * 
	 */
	private static final long serialVersionUID = 3560996560484126700L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;

	@Column(name = "promotion_id", length = 12 )
	private int promotionId;

	@Column(name = "create_time", length = 19 )
	private Date createTime;


	public TzzUserRecommend(){	
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

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzUserRecommend other = (TzzUserRecommend) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userId, other.userId) && Objects.equal(this.promotionId, other.promotionId) && Objects.equal(this.createTime, other.createTime);
	}

	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.promotionId,this.createTime);
	}
}

