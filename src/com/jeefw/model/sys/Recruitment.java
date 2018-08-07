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
import com.jeefw.model.sys.param.RoleParameter;

import core.support.DateTimeSerializer;

/**
 * Recruitment 实体类
 */
@Entity
@Table(name = "recruitment")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions",
		"sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Recruitment extends RoleParameter {

	/**
		 * 
		 */
	private static final long serialVersionUID = -4451280590385599017L;

	// 各个字段的含义请查阅文档的数据库结构部分
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 60)
	private String name;
	
	@Column(name = "begin_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date begin_date;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date end_date;
	
	@Column(name = "free_reservation", length = 1)
	private int free_reservation;
	
	@Column(name = "pay_need", length = 1)
	private int pay_need;

	@Column(name = "participation", length = 12)
	private int participation;
	
	@Column(name = "price", length = 10)
	private float price;
	
	@Column(name = "brief", length = 255)
	private String brief;
	
	@Column(name = "creater", length = 255)
	private String creater;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	@Column(name = "img", length = 255)
	private String img;

	@Column(name = "content")
	private String content;

	@Column(name = "everyday_limit", length = 8)
	private int everyday_limit;
	
	@Column(name = "contact_address", length = 256)
	private String contact_address;
	
	@Column(name = "coordinate", length = 32)
	private String coordinate;
	
	@Column(name = "themeid", length = 12)
	private int themeid;

	@Column(name = "theme_name", length = 120)
	private String themename;
	
	@Column(name = "distance", length = 16)
	private Double distance;
	
	@Column(name = "is_publish", length = 1)
	private int is_publish;

	@Column(name = "status", length = 1)
	private Integer status;
	
	@Column(name = "is_weekend", length = 1)
	private int is_weekend;
	
	@Column(name = "approval_opinion", length = 256)
	private String approval_opinion;
	
	@Column(name = "reservationnum" , length = 12)
	private int reservationnum;

	public Recruitment() {
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

	public int getFree_reservation() {
		return free_reservation;
	}

	public void setFree_reservation(int free_reservation) {
		this.free_reservation = free_reservation;
	}

	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public int getPay_need() {
		return pay_need;
	}

	public void setPay_need(int pay_need) {
		this.pay_need = pay_need;
	}


	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getReservationnum() {
		return reservationnum;
	}

	public void setReservationnum(int reservationnum) {
		this.reservationnum = reservationnum;
	}

	public int getEveryday_limit() {
		return everyday_limit;
	}

	public void setEveryday_limit(int everyday_limit) {
		this.everyday_limit = everyday_limit;
	}

	public int getIs_publish() {
		return is_publish;
	}

	public void setIs_publish(int is_publish) {
		this.is_publish = is_publish;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Recruitment other = (Recruitment) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.name, other.name)
				&& Objects.equal(this.brief, other.brief) && Objects.equal(this.content, other.content);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.name, this.brief, this.content);
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getIs_weekend() {
		return is_weekend;
	}

	public void setIs_weekend(int is_weekend) {
		this.is_weekend = is_weekend;
	}

	public String getApproval_opinion() {
		return approval_opinion;
	}

	public void setApproval_opinion(String approval_opinion) {
		this.approval_opinion = approval_opinion;
	}

	public int getParticipation() {
		return participation;
	}

	public void setParticipation(int participation) {
		this.participation = participation;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getThemeid() {
		return themeid;
	}

	public void setThemeid(int themeid) {
		this.themeid = themeid;
	}

	public String getThemename() {
		return themename;
	}

	public void setThemename(String themename) {
		this.themename = themename;
	}

}
