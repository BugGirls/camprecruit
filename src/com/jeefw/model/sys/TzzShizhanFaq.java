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
import com.jeefw.model.sys.param.TzzShizhanFaqParameter;

   /**
    * TzzShizhanFaq 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_shizhan_faq" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzShizhanFaq extends  TzzShizhanFaqParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "path_id", length = 12 )
	private int pathId;

	@Column(name = "question", length = 255 )
	private String question;

	@Column(name = "answer", length = 255 )
	private String answer;

	@Column(name = "create_time", length = 19 )
	private Date createTime;


	public TzzShizhanFaq(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setPathId(int pathId){
		this.pathId=pathId;
	}
	public int getPathId(){
		return pathId;
	}

	public void setQuestion(String question){
		this.question=question;
	}
	public String getQuestion(){
		return question;
	}

	public void setAnswer(String answer){
		this.answer=answer;
	}
	public String getAnswer(){
		return answer;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}

 

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzShizhanFaq other = (TzzShizhanFaq) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.pathId, other.pathId) && Objects.equal(this.question, other.question) && Objects.equal(this.answer, other.answer) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.pathId,this.question,this.answer,this.createTime);
	}
}

