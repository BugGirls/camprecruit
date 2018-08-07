package com.jeefw.model.sys;


import java.util.Date;

import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import javax.persistence.Table; 

import org.codehaus.jackson.annotate.JsonIgnoreProperties; 
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzAnswerParameter;

import core.support.DateTimeSerializer;

   /**
    * TzzAnswer 实体类
    * Fri Sep 18 10:05:33 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_answer" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzAnswer extends  TzzAnswerParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;
	
	@Column(name = "question_id", length = 12 )
	private int questionId;
	
	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "adopt", length = 1 )
	private String adopt;
	
	@Column(name = "answer_id", length = 12 )
	private int answerId;
	
	@Column(name = "type", length = 1 )
	private int type;
	
	@Column(name = "zan", length = 1 )
	private int zan;

	@Column(name = "create_time", length = 19 )
	private Date createTime;


	public TzzAnswer(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUserId(int userId){
		this.userId=userId;
	}
	public int getUserId(){
		return userId;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setAdopt(String adopt){
		this.adopt=adopt;
	}
	public String getAdopt(){
		return adopt;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime(){
		return createTime;
	}
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
 

	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzAnswer other = (TzzAnswer) obj;
		return Objects.equal(this.id, other.id) &&
				Objects.equal(this.questionId, other.questionId) &&
				Objects.equal(this.userId, other.userId) && 
				Objects.equal(this.content, other.content) && 
				Objects.equal(this.adopt, other.adopt) && 
				Objects.equal(this.zan, other.zan) && 
				Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.zan,this.questionId,this.content,this.adopt,this.createTime);
	}

	
}

