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
import org.hibernate.annotations.Cache; 
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects; 
import com.jeefw.model.sys.param.TzzUserUploadParameter;

   /**
    * TzzUserUpload 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user_upload" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserUpload extends  TzzUserUploadParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;

	@Column(name = "file", length = 255 )
	private String file;

	@Column(name = "family_id", length = 12 )
	private int familyId;

	@Column(name = "name", length = 255 )
	private String name;

	@Column(name = "detail", length = 255 )
	private String detail;

	@Column(name = "state", length = 1 )
	private String state;

	@Column(name = "result", length = 255 )
	private String result;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;


	public TzzUserUpload(){	
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

	public void setFile(String file){
		this.file=file;
	}
	public String getFile(){
		return file;
	}

	public void setFamilyId(int familyId){
		this.familyId=familyId;
	}
	public int getFamilyId(){
		return familyId;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setDetail(String detail){
		this.detail=detail;
	}
	public String getDetail(){
		return detail;
	}

	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
	}

	public void setResult(String result){
		this.result=result;
	}
	public String getResult(){
		return result;
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
		final TzzUserUpload other = (TzzUserUpload) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userId, other.userId) && Objects.equal(this.file, other.file) && Objects.equal(this.familyId, other.familyId) && Objects.equal(this.name, other.name) && Objects.equal(this.detail, other.detail) && Objects.equal(this.state, other.state) && Objects.equal(this.result, other.result) && Objects.equal(this.createTime, other.createTime);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.file,this.familyId,this.name,this.detail,this.state,this.result,this.createTime);
	}
}

