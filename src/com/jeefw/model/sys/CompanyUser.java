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
import com.jeefw.model.sys.param.TzzUserParameter;

import core.support.DateTimeSerializer;

   /**
    * CompanyUser 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="company_user" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class CompanyUser extends  TzzUserParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7801611205097651031L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
  
	@Column(name = "phone", length = 11 )
	private String phone;

	@Column(name = "password", length = 64 )
	private String password;

	@Column(name = "image", length = 256 )
	private String image;

	@Column(name = "email", length = 32 )
	private String email;
	
	@Column(name = "name", length = 32 )
	private String name;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;

	@Column(name = "u_type", length = 12 ,columnDefinition="varchar default 0")
	private String uType;

	@Column(name = "viptype", length = 12 ,columnDefinition="varchar default 0")
	private String viptype;

	@Column(name = "ip", length = 30)
	private String ip;
	
	@Column(name = "last_login_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date lastloginTime;
	
	@Column(name = "state", length = 2)
	private int state;
	
	@Column(name = "stage", length = 2)
	private int stage;
	
	@Column(name = "integrity", length = 2)
	private int integrity;
	
	public CompanyUser(){	
	}

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}

	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}

	public void setImage(String image){
		this.image=image;
	}
	public String getImage(){
		return image;
	}


	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	 
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime(){
		return createTime;
	}

	public void setUType(String uType){
		this.uType=uType;
	}
	public String getUType(){
		return uType;
	}
 
	public void setViptype(String viptype){
		this.viptype=viptype;
	}
	public String getViptype(){
		return viptype;
	}

	public String getuType() {
		return uType;
	}

	public void setuType(String uType) {
		this.uType = uType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getLastloginTime() {
		return lastloginTime;
	}

	public void setLastloginTime(Date lastloginTime) {
		this.lastloginTime = lastloginTime;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIntegrity() {
		return integrity;
	}

	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CompanyUser other = (CompanyUser) obj;
		return  Objects.equal(this.id, other.id) &&
				Objects.equal(this.phone, other.phone) &&
				Objects.equal(this.password, other.password) &&
				Objects.equal(this.image, other.image) &&
				Objects.equal(this.email, other.email) &&
				Objects.equal(this.createTime, other.createTime) && 
				Objects.equal(this.uType, other.uType) && 
				Objects.equal(this.viptype, other.viptype) ;
	}
//	@Override
//	public String toString(){
//		String json = "{id:"+this.id+",realname:'"+this.realname+"',username:'"+this.username+"'}";
//		return json;
//	}
	
	public String getBaseInfo(){
		String json = "{id:"+this.id+",phone:'"+this.phone+"',email:'"+this.email+"',image:'"+this.image+"',uType:'"+this.uType+"'}";
		return json;
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.phone,this.ip,this.password,this.image,this.email,this.createTime,this.uType,this.viptype);
	}
}

