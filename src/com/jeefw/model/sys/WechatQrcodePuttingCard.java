package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatQrcodePuttingCardParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 扫描二维码投放卡券
 *
 * @author Hystar
 * @date 2018/7/13
 */
@Entity
@Table(name = "wechat_qrcode_putting_card")
@org.hibernate.annotations.Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class WechatQrcodePuttingCard extends WechatQrcodePuttingCardParameter {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    /**
     * 加盟商ID
     */
    @Column(name = "alliance_id", nullable = false)
    private Integer allianceId;

    /**
     * 本地卡券ID
     */
    @Column(name = "card_id", nullable = false)
    private Integer cardId;

    /**
     * 微信卡券ID
     * string(32)
     * 必填
     */
    @Column(name = "wechat_card_id", nullable = false, length = 64)
    private String wechatCardId;

    /**
     * 卡券Code码,use_custom_code字段为true的卡券必须填写
     * 非自定义code和导入code模式的卡券不必填写
     * string(20)
     */
    @Column(name = "code", length = 64)
    private String code;

    /**
     * 指定领取者的openid，只有该用户能领取。
     * bind_openid字段为true的卡券必须填写，非指定openid不必填写
     * string(32)
     */
    @Column(name = "openid", length = 64)
    private String openid;

    /**
     * 投放类型
     * QR_CARD      扫码投放
     * string(64)
     */
    @Column(name = "action_name", length = 64)
    private String actionName;

    /**
     * 指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为365天有效
     * long
     */
    @Column(name = "expire_seconds")
    private Long expireSeconds;

    /**
     * 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。
     * 填写true或false。默认false，注意填写该字段时，卡券须通过审核且库存不为0
     */
    @Column(name = "unique_code")
    private Integer uniqueCode;

    /**
     * 领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的 事件推送 中会带上此自定义场景值
     */
    @Column(name = "outer_id")
    private Integer outerId;

    /**
     * outer_id字段升级版本，字符串类型，用户首次领卡时，会通过领取事件推送给商户； 对于会员卡的二维码，用户每次扫码打开会员卡后点击任何url，会将该值拼入url中，方便开发者定位扫码来源
     */
    @Column(name = "outer_str", length = 64)
    private String outerStr;

    /**
     * 获取的二维码ticket，凭借此ticket调用 通过ticket换取二维码接口 可以在有效时间内换取二维码。
     */
    @Column(name = "ticket", nullable = false, length = 256)
    private String ticket;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    @Column(name = "url", nullable = false, length = 256)
    private String url;

    /**
     * 二维码显示地址，点击后跳转二维码页面
     */
    @Column(name = "show_qrcode_url", nullable = false, length = 256)
    private String showQrcodeUrl;

    public WechatQrcodePuttingCard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShowQrcodeUrl() {
        return showQrcodeUrl;
    }

    public void setShowQrcodeUrl(String showQrcodeUrl) {
        this.showQrcodeUrl = showQrcodeUrl;
    }

    public Integer getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Integer allianceId) {
        this.allianceId = allianceId;
    }

    public String getWechatCardId() {
        return wechatCardId;
    }

    public void setWechatCardId(String wechatCardId) {
        this.wechatCardId = wechatCardId;
    }
}
