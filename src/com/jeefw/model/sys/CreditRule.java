package com.jeefw.model.sys;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;

import core.support.ExtJSBaseParameter;

   /**
    * CreditRule 实体类
    * 
    */ 
@Entity
@Table(name ="credit_rule" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CreditRule extends  ExtJSBaseParameter{


/**
	 * 
	 */
	private static final long serialVersionUID = 6867916971295728205L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name", length = 64 )
	private String name;

	@Column(name = "num", length = 5 )
	private int num;
	
	@Column(name = "times", length = 5 )
	private int times;
	
	@Column(name = "type", length = 1 )
	private int type;
	
	
	public CreditRule(){	
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getTimes() {
		return times;
	}


	public void setTimes(int times) {
		this.times = times;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CreditRule other = (CreditRule) obj;
		return Objects.equal(this.id, other.id)  && Objects.equal(this.name, other.name)  ;
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.name);
	}
}

