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
    * TzzUserCreditRecord 实体类
    * 
    */ 
@Entity
@Table(name ="tzz_user_credit_record" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserCreditRecord extends  ExtJSBaseParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = 6867916971295728205L;
	
	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_id", length = 12 )
	private int userId;
	
	@Column(name = "origin", length = 12 )
	private String origin;

	@Column(name = "origin_name", length = 64 )
	private String originName;

	@Column(name = "type", length = 1 )
	private int type;
	
	@Column(name = "quantity", length = 12)
	private int quantity;
	
	@Column(name = "count", length = 12)
	private Long count;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;
	
	@Column(name = "validtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date validtime;
	
	public TzzUserCreditRecord(){	
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getOriginName() {
		return originName;
	}


	public void setOriginName(String originName) {
		this.originName = originName;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getValidtime() {
		return validtime;
	}


	public void setValidtime(Date validtime) {
		this.validtime = validtime;
	}


	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzUserCreditRecord other = (TzzUserCreditRecord) obj;
		return Objects.equal(this.id, other.id) ;
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id);
	}
}

