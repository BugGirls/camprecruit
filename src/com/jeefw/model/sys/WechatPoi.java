package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatPoiParameter;
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
 * 微信门店
 *
 * @author Hystar
 * @date 2018/7/24
 */
@Entity
@Table(name = "wechat_poi")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag"})
public class WechatPoi extends WechatPoiParameter {

    /**
     * 商户自己的ID，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
     * string(64)
     */
    @Id
    @Column(name = "sid", nullable = false, length = 64)
    private String sid;

    @Column(name = "alliance_id", nullable = false)
    private Integer allianceId;

    /**
     * 门店状态
     */
    @Column(name = "poi_status")
    private Integer poiStatus;

    /**
     * 门店可用状态，将标记门店相应审核状态，只有审核通过状态，才能进行更新
     */
    @Column(name = "available_state")
    private Integer availableState;

    /**
     * 门店更新状态，在更新提交后，该状态变为1，直到审核通过，状态变为0
     */
    @Column(name = "update_status")
    private Integer updateStatus;

    /**
     * 门店ID
     * string(64)
     * 必填
     */
    @Column(name = "poi_id", length = 64)
    private String poiId;

    /**
     * 门店名称，不能为空，15个汉字或30个英文字符内
     * string(64)
     * 必填
     */
    @Column(name = "business_name", nullable = false, length = 64)
    private String businessName;

    /**
     * 分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店） 20 个字 以内
     * string(64)
     * 必填
     */
    @Column(name = "branch_name", nullable = false, length = 64)
    private String branchName;

    /**
     * 门店所在的省份（直辖市填城市名,如：北京 市） 10个字以内
     * string(32)
     * 必填
     */
    @Column(name = "province", nullable = false, length = 32)
    private String province;

    /**
     * 门店所在的城市 10个字 以内
     * string(32)
     * 必填
     */
    @Column(name = "city", nullable = false, length = 32)
    private String city;

    /**
     * 门店所在地区 10个字 以内
     * string(32)
     * 必填
     */
    @Column(name = "district", nullable = false, length = 32)
    private String district;

    /**
     * 门店所在的详细街道地址（不要填写省市信息）
     * string(128)
     * 必填
     */
    @Column(name = "address", nullable = false, length = 128)
    private String address;

    /**
     * 门店的电话（纯数字，区号、分机号均由“-”隔开）
     * string(32)
     * 必填
     */
    @Column(name = "telephone", nullable = false, length = 32)
    private String telephone;

    /**
     * 门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
     * string(128)
     * 必填
     */
    @Column(name = "categories", nullable = false, length = 128)
    private String categories;

    /**
     * 坐标类型： 1 为火星坐标 2 为sogou经纬度 3 为百度经纬度 4 为mapbar经纬度 5 为GPS坐标 6 为sogou墨卡托坐标 注：高德经纬度无需转换可直接使用
     * int
     * 必填
     */
    @Column(name = "offset_type", nullable = false)
    private Integer offsetType;

    /**
     * 门店所在地理位置的经度
     * double
     * 必填
     */
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    /**
     * 门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）
     * double
     * 必填
     */
    @Column(name = "latitude", nullable = false)
    private Double latitude;


    // 门店服务信息字段

    /**
     * 图片列表，url 形式，可以有多张图片，尺寸为 640*340px。必须为上一接口生成的url。 图片内容不允许与门店不相关，不允许为二维码、员工合照（或模特肖像）、营业执照、无门店正门的街景、地图截图、公交地铁站牌、菜单截图等
     * text
     */
    @Column(name = "photo_urls")
    private String photoUrls;

    /**
     * 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容 200字以内
     * string(1024)
     */
    @Column(name = "recommend", length = 1024)
    private String recommend;

    /**
     * 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     * string(512)
     */
    @Column(name = "special", length = 512)
    private String special;

    /**
     * 商户简介，主要介绍商户信息等 300字以内
     * string(1024)
     */
    @Column(name = "introduction", length = 1024)
    private String introduction;

    /**
     * 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     * string(32)
     */
    @Column(name = "open_time", length = 32)
    private String openTime;

    /**
     * 人均价格，大于0 的整数
     */
    @Column(name = "avg_price")
    private Integer avgPrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核结果，成功success 或失败fail
     */
    @Column(name = "result", length = 32)
    private String result;

    /**
     * 成功的通知信息，或审核失败的驳回理由
     */
    @Column(name = "msg", length = 512)
    private String msg;

    public WechatPoi() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getPoiStatus() {
        return poiStatus;
    }

    public void setPoiStatus(Integer poiStatus) {
        this.poiStatus = poiStatus;
    }

    public Integer getAvailableState() {
        return availableState;
    }

    public void setAvailableState(Integer availableState) {
        this.availableState = availableState;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getOffsetType() {
        return offsetType;
    }

    public void setOffsetType(Integer offsetType) {
        this.offsetType = offsetType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Integer allianceId) {
        this.allianceId = allianceId;
    }
}
