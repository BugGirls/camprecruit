package com.jeefw.model.sys;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;
import com.jeefw.model.sys.param.RoleParameter;

/**
 * 角色的实体类
 * @ 
 */
@Entity
@Table(name = "role")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Role extends RoleParameter {

	// 各个字段的含义请查阅文档的数据库结构部分
	private static final long serialVersionUID = 6019103858711599150L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "role_key", length = 40, nullable = false, unique = true)
	private String roleKey;
	@Column(name = "role_value", length = 40, nullable = false)
	private String roleValue;
	@Column(name = "description", length = 200)
	private String description;
	@ElementCollection
	@JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") })
	@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<String> permissions;

	public Role() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Role other = (Role) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roleKey, other.roleKey) && Objects.equal(this.roleValue, other.roleValue) && Objects.equal(this.description, other.description)
				&& Objects.equal(this.permissions, other.permissions);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.roleKey, this.roleValue, this.description, this.permissions);
	}

}
