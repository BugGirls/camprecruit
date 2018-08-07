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
import com.jeefw.model.sys.param.TzzUserParameter;

import core.support.DateTimeSerializer;

   /**
    * CompanyNews 实体类
    */ 
@Entity
@Table(name ="company_news" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CompanyNews extends  TzzUserParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5980645322742274839L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
  
	@Column(name = "title", length = 64 )
	private String title;

	@Column(name = "url", length = 256 )
	private String url;

	@Column(name = "userid", length = 256 )
	private int userid;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;
	
	
	public CompanyNews(){	
	}

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}


	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	 
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime(){
		return createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CompanyNews other = (CompanyNews) obj;
		return  Objects.equal(this.id, other.id) &&
				Objects.equal(this.createTime, other.createTime);
	}
//	@Override
//	public String toString(){
//		String json = "{id:"+this.id+",realname:'"+this.realname+"',username:'"+this.username+"'}";
//		return json;
//	}
}

