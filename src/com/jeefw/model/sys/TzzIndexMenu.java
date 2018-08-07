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
    * TzzIndexMenu 实体类
    * 
    */ 
@Entity
@Table(name ="tzz_index_menu" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzIndexMenu extends  ExtJSBaseParameter{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3808208561576256675L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "icon", length = 255 )
	private String icon;

	@Column(name = "title", length = 255 )
	private String title;

	@Column(name = "href", length = 255 )
	private String href;

	@Column(name = "sort", length = 2 )
	private int sort;

	@Column(name = "state", length = 2 )
	private String state;
	
	@Column(name = "roletype", length = 2 )
	private String roletype;

	@Column(name = "create_time")
	@Temporal(TemporalType.DATE) 
	private Date createTime;

	public TzzIndexMenu(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}

	public void setHref(String href){
		this.href=href;
	}
	public String getHref(){
		return href;
	}

	public void setSort(int sort){
		this.sort=sort;
	}
	public int getSort(){
		return sort;
	}

	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
	}
 

	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzIndexMenu other = (TzzIndexMenu) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.icon, other.icon) && Objects.equal(this.title, other.title) && Objects.equal(this.href, other.href) && Objects.equal(this.sort, other.sort) && Objects.equal(this.state, other.state) && Objects.equal(this.roletype, other.roletype);
	}
	
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.icon,this.title,this.href,this.sort,this.state,this.createTime,this.roletype);
	}
	
	
	
}

