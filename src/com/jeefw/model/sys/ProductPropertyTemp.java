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
import com.jeefw.model.sys.param.ProductPropertyTempParameter;

/**
 * 商品属性实体类（大类、小类）
 * @ 
 */
@Entity
<<<<<<< HEAD
@Table(name = "productInfo_propertyTemp")
=======
@Table(name = "productinfo_propertytemp")
>>>>>>> merge project
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ProductPropertyTemp extends ProductPropertyTempParameter {

	private static final long serialVersionUID = 8200122223051499210L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "property_key", length = 20, nullable = false, unique = true)
	private String propertyKey;
	@Column(name = "property_value", length = 40, nullable = false)
	private String propertyValue;
	@Column(name = "sequence")
	private Long sequence;
	@Column(name = "parent_propertykey", length = 20)
	private String parentPropertykey;
	@Column(name = "parent_propertyValue", length = 20)
	private String parentPropertyValue;

	public ProductPropertyTemp() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getParentPropertykey() {
		return parentPropertykey;
	}

	public void setParentPropertykey(String parentPropertykey) {
		this.parentPropertykey = parentPropertykey;
	}

	public String getParentPropertyValue() {
		return parentPropertyValue;
	}

	public void setParentPropertyValue(String parentPropertyValue) {
		this.parentPropertyValue = parentPropertyValue;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProductPropertyTemp other = (ProductPropertyTemp) obj;
		return Objects.equal(this.id, other.id) 
				&& Objects.equal(this.propertyKey, other.propertyKey) 
				&& Objects.equal(this.propertyValue, other.propertyValue) 
				&& Objects.equal(this.sequence, other.sequence)
				&& Objects.equal(this.parentPropertykey, other.parentPropertyValue)
				&& Objects.equal(this.parentPropertyValue, other.parentPropertyValue);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.propertyKey, this.propertyValue, this.sequence, this.parentPropertykey, this.parentPropertyValue);
	}

}
