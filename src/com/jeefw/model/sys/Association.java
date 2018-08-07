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

import core.support.DateTimeSerializer;
import core.support.ExtJSBaseParameter;

   /**
    * AllianceBusiness 实体类
    * 
    */ 
@Entity
@Table(name ="association" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Association extends ExtJSBaseParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461060019503370787L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 60 )
	private String name;

	 
	@Column(name = "isdelete", length = 1 )
	private int isdelete;
	
	@Column(name = "updatetime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updatetime;

	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;

	@Column(name = "creater", length = 255 )
	private String creater;

	@Column(name = "logo", length = 255 )
	private String logo;

	@Column(name = "href", length = 255 )
	private String href;

	
	public Association(){	
	}
	
	public Association(String name,int isdelete,Date updatetime,String logo,String href){	
		this.name = name;
		this.updatetime = updatetime;
		this.logo= logo;
		this.isdelete = isdelete;
		this.href = href;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Association other = (Association) obj;
		return Objects.equal(this.id, other.id)  && Objects.equal(this.name, other.name) ;
	}

	public int hashCode() {
		return Objects.hashCode(this.id,this.name);
	}
}

