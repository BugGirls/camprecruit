package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatMemberStoresParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 微信店铺会员
 *
 * @author Hystar
 * @date 2018/8/13
 */
@Entity
@Table(name = "wechat_member_stores")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag"})
public class WechatMemberStores extends WechatMemberStoresParameter {

    /**
     * 会员ID
     */
    @Id
    @Column(name = "member_id", nullable = false, length = 64)
    private String memberId;

    /**
<<<<<<< HEAD
     * 公众号appId
     */
    @Column(name = "app_id", nullable = false, length = 64)
    private String appId;

    /**
=======
>>>>>>> merge project
     * 会员所属店铺
     */
    @Column(name = "poi", length = 64)
    private String poi;

    /**
     * 是否关注该公众号，值为0时，代表此用户没有关注该公众号，其余值为空
     */
    @Column(name = "subscribe", nullable = false)
    private Integer subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     */
    @Column(name = "openid", length = 64)
    private String openid;

    /**
     * 用户的昵称
     */
    @Column(name = "nickname", length = 64)
    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 用户所在城市
     */
    @Column(name = "city", length = 32)
    private String city;

    /**
     * 用户所在国家
     */
    @Column(name = "country", length = 32)
    private String country;

    /**
     * 用户所在省份
     */
    @Column(name = "province", length = 32)
    private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    @Column(name = "language", length = 32)
    private String language;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    @Column(name = "head_img_url", length = 256)
    private String headImgUrl;

    /**
     * 用户关注时间，如果用户曾多次关注，则取最后关注时间
     */
    @Column(name = "subscribe_time")
    private Date subscribeTime;

    /**
     * 公众号运营者对粉丝的备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 用户所在的分组ID
     */
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 返回用户关注的渠道来源
     * ADD_SCENE_SEARCH 公众号搜索
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移
     * ADD_SCENE_PROFILE_CARD 名片分享
     * ADD_SCENE_QR_CODE 扫描二维码
     * ADD_SCENEPROFILE LINK 图文页内名称点击
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单
     * ADD_SCENE_PAID 支付后关注
     * ADD_SCENE_OTHERS 其他
     */
    @Column(name = "subscribe_scene", length = 32)
    private String subscribeScene;

    /**
     * 二维码扫码场景（开发者自定义）
     */
    @Column(name = "qr_scene")
    private Integer qrScene;

    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    @Column(name = "qr_scene_str", length = 128)
    private String qrSceneStr;

    public WechatMemberStores() {
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public Integer getQrScene() {
        return qrScene;
    }

    public void setQrScene(Integer qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
