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
@Table(name ="cooperation_colleges" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CooperationColleges extends ExtJSBaseParameter{

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

	@Column(name = "brief", length = 255 )
	private String brief;

	@Column(name = "content", length = 255 )
	private String content;

	@Column(name = "type", length = 20 )
	private int type;
	
	@Column(name = "xingzhi", length = 20 )
	private int xingzhi;
	 
	@Column(name = "isdelete", length = 1 )
	private int isdelete;
	
	@Column(name = "isniubi", length = 1 )
	private int isniubi;
	
	@Column(name = "ishenniubi", length = 1 )
	private int ishenniubi;
	
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
	

	
	public CooperationColleges(){	
	}
	
	public CooperationColleges(String name,String brief,String content,int type,int isdelete,Date updatetime,String logo,int xingzhi,int isniubi,int ishenniubi){	
		this.name = name;
		this.brief = brief;
		this.content = content;
		this.type = type;
		this.updatetime = updatetime;
		this.logo= logo;
		this.isdelete = isdelete;
		this.xingzhi = xingzhi;
		this.isniubi= isniubi;
		this.ishenniubi = ishenniubi;
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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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


	public int getXingzhi() {
		return xingzhi;
	}

	public void setXingzhi(int xingzhi) {
		this.xingzhi = xingzhi;
	}

	public int getIsniubi() {
		return isniubi;
	}

	public void setIsniubi(int isniubi) {
		this.isniubi = isniubi;
	}

	public int getIshenniubi() {
		return ishenniubi;
	}

	public void setIshenniubi(int ishenniubi) {
		this.ishenniubi = ishenniubi;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CooperationColleges other = (CooperationColleges) obj;
		return Objects.equal(this.id, other.id)  && Objects.equal(this.name, other.name) && Objects.equal(this.brief, other.brief) && Objects.equal(this.content, other.content);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.name,this.brief,this.content);
	}
}

