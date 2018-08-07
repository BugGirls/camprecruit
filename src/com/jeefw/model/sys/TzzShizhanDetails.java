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
import com.jeefw.model.sys.param.TzzShizhanDetailsParameter;

   /**
    * TzzShizhanDetails 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_shizhan_details" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzShizhanDetails extends  TzzShizhanDetailsParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "tixiku_id", length = 12 )
	private int tixikuId;

	@Column(name = "course_id", length = 12 )
	private int courseId;


	public TzzShizhanDetails(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setTixikuId(int tixikuId){
		this.tixikuId=tixikuId;
	}
	public int getTixikuId(){
		return tixikuId;
	}

	public void setCourseId(int courseId){
		this.courseId=courseId;
	}
	public int getCourseId(){
		return courseId;
	}

 

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzShizhanDetails other = (TzzShizhanDetails) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.tixikuId, other.tixikuId) && Objects.equal(this.courseId, other.courseId);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.tixikuId,this.courseId);
	}
}

