package com.jeefw.model.sys;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户核销卡券信息
 *
 * @author Hystar
 * @date 2018/7/23
 */
@Entity
@Table(name = "wechat_user_consume_card")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class WechatUserConsumeCard {

    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;

    /**
     * 领券方openid
     */
    @Column(name = "openid", length = 64, nullable = false)
    private String openid;

    /**
     * 卡券ID
     */
    @Column(name = "card_id", length = 128, nullable = false)
    private String cardId;

    /**
     * 卡券Code码
     */
    @Column(name = "user_card_code", nullable = false, length = 128)
    private String userCardCode;

    /**
     * 核销来源。支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
     */
    @Column(name = "consume_source", nullable = false, length = 32)
    private String consumeSource;

    /**
     * 门店名称，当前卡券核销的门店名称（只有通过自助核销和买单核销时才会出现该字段）
     */
    @Column(name = "location_name", length = 64)
    private String locationName;

    /**
     *
     */
    @Column(name = "location_id")
    private Integer locationId;

    /**
     * 核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）
     */
    @Column(name = "staff_open_id")
    private String staffOpenId;

    /**
     * 自助核销时，用户输入的验证码
     */
    @Column(name = "verify_code", length = 16)
    private String verifyCode;

    /**
     * 自助核销时 ，用户输入的备注金额
     */
    @Column(name = "remark_amount")
    private BigDecimal remarkAmount;

    /**
     * 开发者发起核销时传入的自定义参数，用于进行核销渠道统计
     */
    @Column(name = "outer_str", length = 128)
    private String outerStr;

    /**
     * 消息创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public WechatUserConsumeCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserCardCode() {
        return userCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        this.userCardCode = userCardCode;
    }

    public String getConsumeSource() {
        return consumeSource;
    }

    public void setConsumeSource(String consumeSource) {
        this.consumeSource = consumeSource;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStaffOpenId() {
        return staffOpenId;
    }

    public void setStaffOpenId(String staffOpenId) {
        this.staffOpenId = staffOpenId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public BigDecimal getRemarkAmount() {
        return remarkAmount;
    }

    public void setRemarkAmount(BigDecimal remarkAmount) {
        this.remarkAmount = remarkAmount;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
}
