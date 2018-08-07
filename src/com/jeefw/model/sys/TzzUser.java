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
    * TzzUser 实体类
    * Fri Sep 18 10:05:34 CST 2015  tudou
    */ 
@Entity
@Table(name ="tzz_user" )
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class TzzUser extends  TzzUserParameter{


 
	private static final long serialVersionUID = 8965402916034051636L;

	// id password phone image nickname name email sex qq birthday createTime company level uType viptype totalAmout 
	// 各个字段的含义请查阅文档的数据库结构部分	
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

	@Column(name = "realname", length = 64 )
	private String realname;
	
	@Column(name = "nickname", length = 64 )
	private String nickname;

	@Column(name = "username", length = 20)
	private String username;

	@Column(name = "email", length = 32 )
	private String email;

	@Column(name = "sex", length = 1,columnDefinition="varchar default 0" )
	private String sex;

	@Column(name = "qq", length = 13 )
	private String qq;
	
	@Column(name = "idcardno", length = 18 )
	private String idcardno;

	@Column(name = "birthday")
	@Temporal(TemporalType.DATE) 
	private Date birthday;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime;

	@Column(name = "company", length = 64 )
	private String company;

	@Column(name = "level", length = 12  )
	private String level;

	@Column(name = "u_type", length = 12 ,columnDefinition="varchar default 0")
	private String uType;

	@Column(name = "viptype", length = 12 ,columnDefinition="varchar default 0")
	private String viptype;

	@Column(name = "total_amout", length = 20 ,columnDefinition="float default 0")
	private float totalAmout;

	@Column(name = "ip", length = 30)
	private String ip;

	@Column(name = "sqlpwd", length = 30 )
	private String sqlpwd;

	@Column(name = "sqlname", length = 30)
	private String sqlname;
	
	@Column(name = "access_token", length = 512)
	private String accessToken;

	@Column(name = "refresh_token", length = 512 )
	private String refreshToken;

	@Column(name = "expires_in", length = 30)
	private Integer expiresIn;
	
	@Column(name = "openid", length = 64 )
	private String openid;
	
	@Column(name = "scope", length = 30 )
	private String scope;
	
	@Column(name = "baby_name", length = 30)
	private String babyName;
	
	@Column(name = "baby_birthday")
	@Temporal(TemporalType.DATE) 
	private Date babyBirthday;
	
	@Column(name = "baby_sex", length = 1)
	private int babySex;
	
	@Column(name = "credit_count", length = 15)
	private Long creditCount;

	@Column(name = "children", length = 256)
	private String children;
	
	@Column(name = "from_alliance", length = 12)
	private Integer fromAlliance;
	
	@Column(name = "from_user", length = 12)
	private Integer fromUser;
	
	@Column(name = "puserid", length = 12)
	private Integer puserid;
	
	@Column(name = "schoolid", length = 11)
	private Integer schoolid;
	
	@Column(name = "class_grade", length = 11)
	private int class_grade;
	
	@Column(name = "campsiteid", length = 11)
	private Integer campsiteid;
	
	public TzzUser(){	
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}

	public void setSex(String sex){
		this.sex=sex;
	}
	public String getSex(){
		return sex;
	}

	public void setQq(String qq){
		this.qq=qq;
	}
	public String getQq(){
		return qq;
	}

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}
	public Date getBirthday(){
		return birthday;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	 
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateTime(){
		return createTime;
	}

	public void setCompany(String company){
		this.company=company;
	}
	public String getCompany(){
		return company;
	}

	public void setLevel(String level){
		this.level=level;
	}
	public String getLevel(){
		return level;
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

	public String getSqlpwd() {
		return sqlpwd;
	}

	public void setSqlpwd(String sqlpwd) {
		this.sqlpwd = sqlpwd;
	}

	public String getSqlname() {
		return sqlname;
	}

	public void setSqlname(String sqlname) {
		this.sqlname = sqlname;
	}

	public void setTotalAmout(float totalAmout){
		this.totalAmout=totalAmout;
	}
	public float getTotalAmout(){
		return totalAmout;
	}
  

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public Date getBabyBirthday() {
		return babyBirthday;
	}

	public void setBabyBirthday(Date babyBirthday) {
		this.babyBirthday = babyBirthday;
	}

	public int getBabySex() {
		return babySex;
	}

	public void setBabySex(int babySex) {
		this.babySex = babySex;
	}
	
	public Long getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Long creditCount) {
		this.creditCount = creditCount;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public Integer getFromAlliance() {
		return fromAlliance;
	}

	public void setFromAlliance(Integer fromAlliance) {
		this.fromAlliance = fromAlliance;
	}

	public Integer getFromUser() {
		return fromUser;
	}

	public void setFromUser(Integer fromUser) {
		this.fromUser = fromUser;
	}

	public Integer getPuserid() {
		return puserid;
	}

	public void setPuserid(Integer puserid) {
		this.puserid = puserid;
	}
	
	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public int getClass_grade() {
		return class_grade;
	}

	public void setClass_grade(int class_grade) {
		this.class_grade = class_grade;
	}

	public Integer getCampsiteid() {
		return campsiteid;
	}

	public void setCampsiteid(Integer campsiteid) {
		this.campsiteid = campsiteid;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TzzUser other = (TzzUser) obj;
		return  Objects.equal(this.id, other.id) &&
				Objects.equal(this.phone, other.phone) &&
				Objects.equal(this.password, other.password) &&
				Objects.equal(this.image, other.image) &&
				Objects.equal(this.realname, other.realname) &&
				Objects.equal(this.username, other.username) &&
				Objects.equal(this.email, other.email) &&
				Objects.equal(this.sex, other.sex) && 
				Objects.equal(this.qq, other.qq) && 
				Objects.equal(this.birthday, other.birthday) &&
				Objects.equal(this.createTime, other.createTime) && 
				Objects.equal(this.company, other.company) &&
				Objects.equal(this.level, other.level) &&
				Objects.equal(this.uType, other.uType) && 
				Objects.equal(this.viptype, other.viptype) && 
				Objects.equal(this.ip, other.ip) && 
				Objects.equal(this.sqlname, other.sqlname) && 
				Objects.equal(this.sqlpwd, other.sqlpwd) && 
				Objects.equal(this.totalAmout, other.totalAmout);
	}
//	@Override
//	public String toString(){
//		String json = "{id:"+this.id+",realname:'"+this.realname+"',username:'"+this.username+"'}";
//		return json;
//	}
	
	public String getBaseInfo(){
		String json = "{id:"+this.id+",openid:'"+this.openid+"',realname:'"+this.realname+"',username:'"+this.username+"',sex:'"+this.sex+"',phone:'"+this.phone+"',creditCount:'"+this.creditCount+"',image:'"+this.image+"',uType:'"+this.uType+"'}";
		return json;
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.phone,this.ip,this.sqlname,this.sqlpwd,this.password,this.image,this.realname,this.username,this.email,this.sex,this.qq,this.birthday,this.createTime,this.company,this.level,this.uType,this.viptype,this.totalAmout);
	}
}

