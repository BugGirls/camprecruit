package com.jeefw.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jeefw.model.sys.param.ReplyMsgSetParameter;

/**
 * 公众号回复消息设置的实体类 @
 */
@Entity
@Table(name = "reply_msg_set")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions",
		"sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class ReplyMsgSet extends ReplyMsgSetParameter {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1044598913308220820L;
	
	@Id
	@Column(name = "id", length = 6, nullable = false, unique = true)
	private int id;
	@Column(name = "receive_msg", length = 255)
	private String receive_msg;
	
	@Column(name = "reply_type", length = 32)
	private String reply_type;
	
	@Column(name = "reply_title", length = 64)
	private String reply_title;
	
	@Column(name = "reply_description", length = 255)
	private String reply_description;
	
	@Column(name = "pic_url", length = 128)
	private String pic_url;
	
	@Column(name = "url", length = 255)
	private String url;

	public ReplyMsgSet() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceive_msg() {
		return receive_msg;
	}

	public void setReceive_msg(String receive_msg) {
		this.receive_msg = receive_msg;
	}

	public String getReply_type() {
		return reply_type;
	}

	public void setReply_type(String reply_type) {
		this.reply_type = reply_type;
	}

	public String getReply_title() {
		return reply_title;
	}

	public void setReply_title(String reply_title) {
		this.reply_title = reply_title;
	}

	public String getReply_description() {
		return reply_description;
	}

	public void setReply_description(String reply_description) {
		this.reply_description = reply_description;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
