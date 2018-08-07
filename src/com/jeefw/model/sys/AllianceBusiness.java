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

import com.jeefw.model.sys.param.AllianceBusinessParameter;

import core.support.DateTimeSerializer;

   /**
    * AllianceBusiness 实体类
    * 
    */ 
@Entity
@Table(name ="alliance_business" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class AllianceBusiness extends AllianceBusinessParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461060019503370787L;

	// 各个字段的含义请查阅文档的数据库结构部分	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name", length = 60 )
	private String name;

	@Column(name = "brief", length = 255 )
	private String brief;

	@Column(name = "content", length = 21845 )
	private String content;

	@Column(name = "type", length = 255 )
	private String type;//直营、自营、加盟、连锁
	@Column(name = "typeValue", length = 255 )
	private String typeValue;//直营、自营、加盟、连锁
	 
	@Column(name = "isdelete", length = 1 )
	private int isdelete;
	
	@Column(name = "updatetime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updatetime;

	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createtime;

	@Column(name = "creater", length = 255 )
	private String creater;

	@Column(name = "logo", length = 255 )
	private String logo;
	
	@Column(name = "industry", length = 255 )
	private String industry;//行业
	@Column(name = "industryValue", length = 255 )
	private String industryValue;//行业
	
	@Column(name = "province", length = 255 )
	private String province;//省份
	
	@Column(name = "city", length = 255 )
	private String city;//市区
	
	@Column(name = "address", length = 255 )
	private String address;//地址
	
	@Column(name = "address_coordinate", length = 255 )
	private String addressCoordinate;//坐标
	
	@Column(name = "contacts", length = 255 )
	private String contacts;//联系人
	
	@Column(name = "contacts_phone", length = 255 )
	private String contactsPhone;//联系电话
	
	@Column(name = "business_license", length = 255 )
	private String businessLicense;//营业执照
	
	public AllianceBusiness(){	
	}

	public AllianceBusiness(int id, String name, String brief, String content, String type, String typeValue,
			int isdelete, Date updatetime, Date createtime, String creater, String logo, String industry,
			String industryValue, String province, String city, String address, String addressCoordinate,
			String contacts, String contactsPhone, String businessLicense) {
		this.id = id;
		this.name = name;
		this.brief = brief;
		this.content = content;
		this.type = type;
		this.typeValue = typeValue;
		this.isdelete = isdelete;
		this.updatetime = updatetime;
		this.createtime = createtime;
		this.creater = creater;
		this.logo = logo;
		this.industry = industry;
		this.industryValue = industryValue;
		this.province = province;
		this.city = city;
		this.address = address;
		this.addressCoordinate = addressCoordinate;
		this.contacts = contacts;
		this.contactsPhone = contactsPhone;
		this.businessLicense = businessLicense;
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



	public String getIndustry() {
		return industry;
	}



	public void setIndustry(String industry) {
		this.industry = industry;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getAddressCoordinate() {
		return addressCoordinate;
	}



	public void setAddressCoordinate(String addressCoordinate) {
		this.addressCoordinate = addressCoordinate;
	}



	public String getContacts() {
		return contacts;
	}



	public void setContacts(String contacts) {
		this.contacts = contacts;
	}



	public String getContactsPhone() {
		return contactsPhone;
	}



	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}



	public String getBusinessLicense() {
		return businessLicense;
	}



	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	@JsonSerialize(using = DateTimeSerializer.class)  
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getIndustryValue() {
		return industryValue;
	}

	public void setIndustryValue(String industryValue) {
		this.industryValue = industryValue;
	}
	
}

