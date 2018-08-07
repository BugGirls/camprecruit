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
import com.jeefw.model.sys.param.TzzUserAddressParameter;

   /**
    * TzzUserAddress 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user_address" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUserAddress extends  TzzUserAddressParameter{


// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "user_id", length = 12 )
	private int userId;

	@Column(name = "address", length = 200 )
	private String address;

	@Column(name = "state", length = 1 )
	private String state;
	
	@Column(name = "user_name", length = 50 )
	private String userName;
	
	@Column(name = "phone", length = 16 )
	private String phone;
	
	@Column(name = "postcode", length = 8 )
	private String postcode;
	
	@Column(name = "province", length = 255 )
	private String province;
	
	@Column(name = "city", length = 255 )
	private String city;
	
	@Column(name = "county", length = 255 )
	private String county;


	public TzzUserAddress(){	
	}
	
	
	
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getPostcode() {
		return postcode;
	}



	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getCounty() {
		return county;
	}



	public void setCounty(String county) {
		this.county = county;
	}



	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}

	public void setUserId(int userId){
		this.userId=userId;
	}
	public int getUserId(){
		return userId;
	}

	public void setAddress(String address){
		this.address=address;
	}
	public String getAddress(){
		return address;
	}

	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
	}


	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzUserAddress other = (TzzUserAddress) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userId, other.userId) && Objects.equal(this.address, other.address) && Objects.equal(this.state, other.state)&&Objects.equal(this.userName, other.userName)&&Objects.equal(this.phone,other.phone)&&Objects.equal(this.postcode, other.postcode)&&Objects.equal(this.province, other.province)&&Objects.equal(this.city, other.city)&&Objects.equal(this.county, other.county);
	}
	


	public int hashCode() {
		return Objects.hashCode(this.id,this.userId,this.address,this.state,this.userName,this.phone,this.postcode,this.province,this.city,this.county);
	}
}

