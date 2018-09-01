package com.jeefw.model.sys;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 微信配置数据
 *
 * @author Hystar
 * @date 2018/9/1
 */
@Entity
@Table(name = "wechat_config_data")
@org.hibernate.annotations.Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd","queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class WechatConfigData {

    /**
     * 配置数据ID
     */
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 加盟商ID
     */
    @Column(name = "alliance_id", nullable = false)
    private Integer allianceId;

    /**
     * 微信公众号appid
     */
    @Column(name = "app_Id", length = 32, nullable = false)
    private String appId;

    /**
     * 微信公众号AppSecret
     */
    @Column(name = "app_secret", nullable = false, length = 64)
    private String appSecret;

    /**
     * 微信商户平台商户ID
     */
    @Column(name = "mch_id", length = 32)
    private String mchId;

    /**
     * 微信商户平台商户秘钥
     */
    @Column(name = "mch_key", length = 64)
    private String mchKey;

    /**
     * 关注微信公众号的二维码
     */
    @Column(name = "code_url", nullable = false, length = 512)
    private String codeUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Integer allianceId) {
        this.allianceId = allianceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}
