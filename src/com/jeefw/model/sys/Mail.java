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
import com.jeefw.model.sys.param.MailParameter;

import core.support.DateTimeSerializer;

/**
 * 邮件的实体类
 * @ 
 */
@Entity
@Table(name = "mail")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Mail extends MailParameter {

	// 各个字段的含义请查阅文档的数据库结构部分
	private static final long serialVersionUID = 7965864882079260448L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "user_id", length = 20)
	private long userId;
	@Column(name = "recipient", length = 1000)
	private String recipient;
	@Column(name = "sender", length = 30)
	private String sender;
	@Column(name = "subject", length = 200)
	private String subject;
	@Column(name = "message", columnDefinition = "LONGTEXT")
	private String message;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; 
	public Mail() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		final Mail other = (Mail) obj;
		return Objects.equal(this.id, other.id) &&
				Objects.equal(this.userId, other.userId) &&
				Objects.equal(this.createTime, other.createTime) &&
				Objects.equal(this.recipient, other.recipient) && 
				Objects.equal(this.sender, other.sender) && 
				Objects.equal(this.subject, other.subject) && 
				Objects.equal(this.message, other.message);
	}

	public int hashCode() {
		return Objects.hashCode(this.id,this.createTime,this.userId, this.recipient, this.sender, this.subject, this.message);
	}

}
