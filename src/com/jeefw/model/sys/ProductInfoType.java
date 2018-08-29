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
import com.jeefw.model.sys.param.ProductInfoTypeParameter;

/**
 * 商品分类实体类（大类、小类）
 * @ 
 */
@Table(name = "productinfo_type")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ProductInfoType extends ProductInfoTypeParameter {

	private static final long serialVersionUID = 8200122223051499210L;
	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "type_key", length = 20, nullable = false, unique = true)
	private String typeKey;
	@Column(name = "type_value", length = 40, nullable = false)
	private String typeValue;
	@Column(name = "sequence")
	private Long sequence;
	@Column(name = "parent_typekey", length = 20)
	private String parentTypekey;

	public ProductInfoType() {

	}

	public ProductInfoType(Long id, String typeKey, String typeValue, Long sequence,
			String parentTypekey) {
		this.id = id;
		this.typeKey = typeKey;
		this.typeValue = typeValue;
		this.sequence = sequence;
		this.parentTypekey = parentTypekey;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTypeKey() {
		return typeKey;
	}


	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}


	public String getTypeValue() {
		return typeValue;
	}


	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}


	public Long getSequence() {
		return sequence;
	}


	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}


	public String getParentTypekey() {
		return parentTypekey;
	}


	public void setParentTypekey(String parentTypekey) {
		this.parentTypekey = parentTypekey;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProductInfoType other = (ProductInfoType) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.typeKey, other.typeKey) && Objects.equal(this.typeValue, other.typeValue) && Objects.equal(this.sequence, other.sequence)
				&& Objects.equal(this.parentTypekey, other.parentTypekey);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.typeKey, this.typeValue, this.sequence, this.parentTypekey);
	}

}
