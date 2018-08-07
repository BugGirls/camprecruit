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
import com.jeefw.model.sys.param.EmailphoneParameter;

import core.support.DateTimeSerializer;

/**
 * Emailphone 实体类
 * 2015/10/08 10:50:58  tudou
 */
@Entity
@Table(name = "emailphone")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag"})
public class Emailphone extends EmailphoneParameter {


    /**
     *
     */
    private static final long serialVersionUID = -8028859521911727716L;

    // 各个字段的含义请查阅文档的数据库结构部分
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "pwd", length = 50)
    private String pwd;

    @Column(name = "smtp", length = 50)
    private String smtp;

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "smspwd", length = 50)
    private String smspwd;

    @Column(name = "channel", length = 20)
    private String channel;

    @Column(name = "estate", length = 1)
    private String estate;

    @Column(name = "pstate", length = 1)
    private String pstate;


    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    public Emailphone() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getEstate() {
        return estate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPstate() {
        return pstate;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSmspwd() {
        return smspwd;
    }

    public void setSmspwd(String smspwd) {
        this.smspwd = smspwd;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Emailphone other = (Emailphone) obj;
        return Objects.equal(this.id, other.id) &&
                Objects.equal(this.pwd, other.pwd) &&
                Objects.equal(this.email, other.email) &&
                Objects.equal(this.smtp, other.smtp) &&
                Objects.equal(this.smspwd, other.smspwd) &&
                Objects.equal(this.channel, other.channel) &&
                Objects.equal(this.phone, other.phone) &&
                Objects.equal(this.estate, other.estate) &&
                Objects.equal(this.pstate, other.pstate) &&
                Objects.equal(this.createTime, other.createTime);
    }


    public int hashCode() {
        return Objects.hashCode(this.id, this.email, this.smspwd, this.channel, this.pwd, this.smtp, this.phone, this.estate, this.pstate, this.createTime);
    }
}

