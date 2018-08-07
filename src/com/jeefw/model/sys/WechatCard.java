package com.jeefw.model.sys;

import com.jeefw.model.sys.param.WechatCardParameter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 微信卡券
 *
 * @author Hystar
 * @date 2018/7/10
 */
@Entity
@Table(name = "wechat_card")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag"})
public class WechatCard extends WechatCardParameter {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    /**
     * 加盟商
     */
    @Column(name = "alliance_id")
    private Integer allianceId;

    /**
     * 当卡券创建成功后返回的卡券ID
     */
    @Column(name = "card_id", nullable = false, length = 128)
    private String cardId;

    /**
     * 卡券状态
     */
    @Column(name = "card_status", nullable = false)
    private Integer cardStatus;

    /**
     * 卡券类型
     * 必填
     */
    @Column(name = "card_type", nullable = false, length = 32)
    private String cardType;

    // base_info（卡券基础信息）字段-必填字段

    /**
     * 卡券的商户logo，建议像素为300*300
     * string(128)
     * 必填
     */
    @Column(name = "logo_url", nullable = false, length = 256)
    private String logoUrl;

    /**
     * 商户名字,字数上限为12个汉字。
     * string（36）
     * 必填
     */
    @Column(name = "brand_name", nullable = false, length = 36)
    private String brandName;

    /**
     * 码型：
     * "CODE_TYPE_TEXT"          文 本                   仅适用于输码核销
     * "CODE_TYPE_BARCODE"       一维码                  适用于扫码/输码核销
     * "CODE_TYPE_QRCODE"        二维码                  适用于扫码/输码核销
     * "CODE_TYPE_ONLY_QRCODE",  二维码无code显示         仅适用于扫码核销
     * "CODE_TYPE_ONLY_BARCODE"  一维码无code显示         仅适用于扫码核销
     * CODE_TYPE_NONE，          不显示code和条形码类型    仅适用于线上核销，开发者须自定义跳转链接跳转至H5页面，允许用户核销掉卡券，自定义cell的名称可以命名为“立即使用”
     * string(16)
     * 必填
     */
    @Column(name = "code_type", nullable = false, length = 32)
    private String codeType;

    /**
     * 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)
     * string（27）
     * 必填
     */
    @Column(name = "title", nullable = false, length = 27)
    private String title;

    /**
     * 券颜色。按色彩规范标注填写Color010-Color100。
     * string（16）
     * 必填
     */
    @Column(name = "color", nullable = false, length = 16)
    private String color;

    /**
     * 卡券使用提醒，字数上限为16个汉字
     * string（48）
     * 必填
     */
    @Column(name = "notice", nullable = false, length = 48)
    private String notice;

    /**
     * 卡券使用说明，字数上限为1024个汉字
     * string（3072）
     * 必填
     */
    @Column(name = "description", nullable = false, length = 3072)
    private String description;

    // sku属性

    /**
     * 卡券库存的数量，上限为100000000
     * int
     * 必填
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // date_info属性

    /**
     * 使用时间的类型
     * string
     * 必填
     */
    @Column(name = "date_type", nullable = false, length = 32)
    private String dateType;

    /**
     * type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间（秒）
     * unsigned int
     * 必填
     */
    @Column(name = "begin_timestamp")
    private Long beginTimestamp;

    /**
     * type为DATE_TYPE_FIX_TIME_RANGE时专用，表示结束时间（秒）
     * 建议设置为截止日期的23:59:59过期
     * unsigned int
     * 必填
     */
    @Column(name = "end_timestamp")
    private Long endTimestamp;

    /**
     * type为DATE_TYPE_FIX_TERM时专用
     * 表示自领取后多少天内有效，不支持填写0。（单位为天）
     * 必填
     */
    @Column(name = "fixed_term")
    private Integer fixedTerm;

    /**
     * type为DATE_TYPE_FIX_TERM时专用
     * 表示自领取后多少天开始生效，领取后当天生效填写0。（单位为天）
     * 必填
     */
    @Column(name = "fixed_begin_term")
    private Integer fixedBeginTerm;

    // base_info（卡券基础信息）字段-非必填字段

    /**
     * 是否自定义Code码 。填写true或false，默认为false
     * bool
     */
    @Column(name = "use_custom_code")
    private Integer useCustomCode;

    /**
     * 填入 GET_CUSTOM_CODE_MODE_DEPOSIT 表示该卡券为预存code模式卡券
     * 须导入超过库存数目的自定义code后方可投放
     * 填入该字段后，quantity字段须为0
     * 须导入code 后再增加库存
     * string(32)
     */
    @Column(name = "get_custom_code_mode", length = 32)
    private String getCustomCodeMode;

    /**
     * 是否指定用户领取，填写true或false 。默认为false。
     * 通常指定特殊用户群体 投放卡券或防止刷券时选择指定用户领取。
     * bool
     */
    @Column(name = "bind_openid")
    private Integer bindOpenid;

    /**
     * 客服电话
     * string(24)
     */
    @Column(name = "service_phone", length = 24)
    private String servicePhone;

    /**
     * 门店位置poiid。 调用 POI门店管理接口 获取门店位置poiid。
     * 具备线下门店 的商户为必填。
     * array
     */
    @Column(name = "location_ids", length = 256)
    private String locationIds;

    /**
     * 设置本卡券支持全部门店，与location_id_list互斥
     * bool
     */
    @Column(name = "use_all_location")
    private Integer useAllLocation;

    /**
     * 卡券顶部居中的按钮，仅在卡券状态正常(可以核销)时显示
     * string(18)
     */
    @Column(name = "center_title", length = 18)
    private String centerTitle;

    /**
     * 显示在入口下方的提示语 ，仅在卡券状态正常(可以核销)时显示。
     * string（24）
     */
    @Column(name = "center_sub_title", length = 24)
    private String centerSubTitle;

    /**
     * 顶部居中的url ，仅在卡券状态正常(可以核销)时显示
     * string（128）
     */
    @Column(name = "center_url", length = 128)
    private String centerUrl;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该公众号绑定的小程序
     * string（128）
     */
    @Column(name = "center_app_brand_user_name", length = 128)
    private String centerAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     * string（128）
     */
    @Column(name = "center_app_brand_pass", length = 128)
    private String centerAppBrandPass;

    /**
     * 自定义跳转外链的入口名字。
     * string（15）
     */
    @Column(name = "custom_url_name", length = 15)
    private String customUrlName;

    /**
     * 显示在入口右侧的提示语。
     * string（18）
     */
    @Column(name = "custom_url_sub_title", length = 18)
    private String customUrlSubTitle;

    /**
     * 自定义跳转的URL
     * string（128）
     */
    @Column(name = "custom_url", length = 128)
    private String customUrl;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该 公众号绑定的小程序
     * string（128）
     */
    @Column(name = "custom_app_brand_user_name", length = 128)
    private String customAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     * string（128）
     */
    @Column(name = "custom_app_brand_pass", length = 128)
    private String customAppBrandPass;

    /**
     * 营销场景的自定义入口名称
     * string（15）
     */
    @Column(name = "promotion_url_name", length = 15)
    private String promotionUrlName;

    /**
     * 显示在营销入口右侧的提示语
     * string（18）
     */
    @Column(name = "promotion_url_sub_title", length = 18)
    private String promotionUrlSubTitle;

    /**
     * 入口跳转外链的地址链接
     * string（128）
     */
    @Column(name = "promotion_url", length = 128)
    private String promotionUrl;

    /**
     * 卡券跳转的小程序的user_name，仅可跳转该公众号绑定的小程序
     * string（128）
     */
    @Column(name = "promotion_app_brand_user_name", length = 128)
    private String promotionAppBrandUserName;

    /**
     * 卡券跳转的小程序的path
     * string（128）
     */
    @Column(name = "promotion_app_brand_pass", length = 128)
    private String promotionAppBrandPass;

    /**
     * 每人可领券的数量限制,不填写默认为50。
     * int
     */
    @Column(name = "get_limit")
    private Integer getLimit;

    /**
     * 每人可核销的数量限制,不填写默认为50。
     * int
     */
    @Column(name = "use_limit")
    private Integer useLimit;

    /**
     * 卡券领取页面是否可分享
     * bool
     */
    @Column(name = "can_share")
    private Integer canShare;

    /**
     * 卡券是否可转赠
     * bool
     */
    @Column(name = "can_give_friend")
    private Integer canGiveFriend;


    // Advanced_info（卡券高级信息）字段

    /**
     * 购买xx可用类型门槛，仅用于兑换 ，填入后自动拼写购买xxx可用
     * string（ 512 ）
     */
    @Column(name = "object_use_for", length = 512)
    private String objectUseFor;

    /**
     * 不可以与其他类型共享门槛，
     * 填写false时系统将在使用须知里 拼写“不可与其他优惠共享”
     * 填写true时系统将在使用须知里 拼写“可与其他优惠共享”， 默认为true
     * bool
     */
    @Column(name = "can_use_with_other_discount")
    private Integer canUseWithOtherDiscount;

    /**
     * 封面摘要简介
     * string（24）
     */
    @Column(name = "card_abstract", length = 24)
    private String cardAbstract;

    /**
     * 封面图片列表，仅支持填入一个封面图片链接，
     * 上传图片接口 上传获取图片获得链接，填写非CDN链接会报错，并在此填入。
     * 建议图片尺寸像素850*350
     * string（128）
     */
    @Column(name = "icon_url_list", length = 256)
    private String iconUrlList;

    /**
     * 图片链接，必须调用 上传图片接口 上传图片获得链接，并在此填入， 否则报错
     * string（128）
     */
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    /**
     * 图文描述
     * string（512）
     */
    @Column(name = "text", length = 512)
    private String text;

    /**
     * 商家服务类型
     * BIZ_SERVICE_DELIVER 外卖服务；
     * BIZ_SERVICE_FREE_PARK 停车位；
     * BIZ_SERVICE_WITH_PET 可带宠物；
     * BIZ_SERVICE_FREE_WIFI 免费wifi，
     * 可多选
     * array
     */
    @Column(name = "business_service", length = 128)
    private String businessService;

    /**
     * 限制类型枚举值：支持填入
     * MONDAY 周一
     * TUESDAY 周二
     * WEDNESDAY 周三
     * THURSDAY 周四
     * FRIDAY 周五
     * SATURDAY 周六
     * SUNDAY 周日
     * 此处只控制显示， 不控制实际使用逻辑，不填默认不显示
     * string（24 ）
     */
    @Column(name = "advanced_type", length = 24)
    private String advancedType;

    /**
     * 当前type类型下的起始时间（小时）
     * 如当前结构体内填写了MONDAY， 此处填写了10，则此处表示周一 10:00可用
     * int
     */
    @Column(name = "begin_hour")
    private Integer beginHour;

    /**
     * 当前type类型下的起始时间（分钟）
     * 如当前结构体内填写了MONDAY， begin_hour填写10，此处填写了59， 则此处表示周一 10:59可用
     * int
     */
    @Column(name = "begin_minute")
    private Integer beginMinute;

    /**
     * 当前type类型下的结束时间（小时）
     * 如当前结构体内填写了MONDAY， 此处填写了20， 则此处表示周一 10:00-20:00可用
     * int
     */
    @Column(name = "end_hour")
    private Integer endHour;

    /**
     * 当前type类型下的结束时间（分钟）
     * 如当前结构体内填写了MONDAY， begin_hour填写10，此处填写了59， 则此处表示周一 10:59-00:59可用
     * int
     */
    @Column(name = "end_minute")
    private Integer endMinute;

    // 卡券专属字段
    // 代金券专用

    /**
     * 代金券专用
     * 表示减免金额。（单位为分）
     */
    @Column(name = "reduce_cost")
    private Integer reduceCost;

    /**
     * 代金券专用
     * 表示起用金额（单位为分）,如果无起用门槛则填0。
     * int
     */
    @Column(name = "least_cost")
    private Integer leastCost;

    // 折扣券专用

    /**
     * 折扣券专用，
     * 表示打折额度（百分比）。填30就是七折。
     * int
     */
    @Column(name = "discount")
    private Integer discount;

    // 兑换券专用

    /**
     * 兑换券专用
     * 填写兑换内容的名称。
     * string(3072)
     */
    @Column(name = "gift", length = 3072)
    private String gift;

    // 优惠券专用

    /**
     * 优惠券专用
     * 填写优惠详情。
     * string(3072)
     */
    @Column(name = "default_detail", length = 3072)
    private String defaultDetail;

    /**
     * 团购券专用
     * 团购详情
     * string( 3072 )
     */
    @Column(name = "deal_detail", length = 3072)
    private String dealDetail;

    // 会员卡专属字段
    /**
     * 商家自定义会员卡背景图（卡面设计请遵循 微信会员卡自定义背景设计规范 ,像素大小控制在 1000像素*600像素以下）
     * 必填：否
     * string(128)
     */
    @Column(name = "background_pic_url", length = 128)
    private String backgroundPicUrl;

    /**
     * 会员卡特权说明,限制1024汉字
     * 必填：是
     * string(3072)
     */
    @Column(name = "prerogative", length = 3072)
    private String prerogative;

    /**
     * 设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见自动激活
     * 必填：否
     * bool
     */
    @Column(name = "auto_activate")
    private Integer autoActivate;

    /**
     * 显示积分，填写true或false，如填写true，积分相关字段均为必填，若设置为true则后续不可以被关闭
     * 必填：是
     * bool
     */
    @Column(name = "supply_bonus")
    private Integer supplyBonus;

    /**
     * 设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段
     * 必填：否
     * string(128)
     */
    @Column(name = "bonus_url", length = 128)
    private String bonusUrl;

    /**
     * 是否支持储值，填写true或false。如填写true，储值相关字段均为必填，若设置为true则后续不可以被关闭。该字段须开通储值功能后方可使用， 详情见： 获取特殊权限
     * 必填：是
     * bool
     */
    @Column(name = "supply_balance")
    private Integer supplyBalance;

    /**
     * 设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段。
     * 必填：否
     * string(128)
     */
    @Column(name = "balance_url", length = 128)
    private String balanceUrl;

    /**
     * 激活会员卡的url
     * string（128）
     */
    @Column(name = "activate_url", length = 128)
    private String activateUrl;

    /**
     * 会员信息类目半自定义名称，当开发者变更这类类目信息的value值时 可以选择触发系统模板消息通知用户。
     * FIELD_NAME_TYPE_LEVEL 等级
     * FIELD_NAME_TYPE_COUPON 优惠券
     * FIELD_NAME_TYPE_STAMP 印花
     * FIELD_NAME_TYPE_DISCOUNT 折扣
     * FIELD_NAME_TYPE_ACHIEVEMEN 成就
     * FIELD_NAME_TYPE_MILEAGE 里程
     * FIELD_NAME_TYPE_SET_POINTS 集点
     * FIELD_NAME_TYPE_TIMS 次数
     * 必填：否
     * string(24)
     */
    @Column(name = "name_type", length = 24)
    private String nameType;

    /**
     * 会员信息类目自定义名称，当开发者变更这类类目信息的value值时 不会触发系统模板消息通知用户
     * 必填：否
     * string(24)
     */
    @Column(name = "name", length = 24)
    private String name;

    /**
     * 点击类目跳转外链url
     * 必填：否
     * string(128)
     */
    @Column(name = "url_type", length = 128)
    private String urlType;

    /**
     * 积分清零规则。（supply_bonus为true时必填）
     * string（128）
     */
    @Column(name = "bonus_cleared", length = 128)
    private String bonusCleared;

    /**
     * 积分规则。（supply_bonus为true时必填）
     * string（128）
     */
    @Column(name = "bonus_rules", length = 128)
    private String bonusRules;

    /**
     * 储值说明（supply_balance为true时必填）
     * string（128）
     */
    @Column(name = "balance_rules", length = 128)
    private String balanceRules;

    /**
     * 激活会原卡url对应的小程序user_name，仅可跳转该公众号绑定的小程序
     * 必填：否
     * string（128）
     */
    @Column(name = "activate_app_brand_user_name", length = 128)
    private String activateAppBrandUserName;

    /**
     * 激活会原卡url对应的小程序path
     * 必填：否
     * string（128）
     */
    @Column(name = "activate_app_brand_pass", length = 128)
    private String activateAppBrandPass;

    /**
     * 设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。填入该字段后仍需调用接口设置开卡项方可生效，详情见 一键开卡
     * 必填：否
     * bool
     */
    @Column(name = "wx_activate")
    private Integer wxActivate;

    /**
     * 消费金额。以分为单位。
     */
    @Column(name = "cost_money_unit")
    private Integer costMoneyUnit;

    /**
     * 对应增加的积分。
     */
    @Column(name = "increase_bonus")
    private Integer increaseBonus;

    /**
     * 用户单次可获取的积分上限
     */
    @Column(name = "max_increase_bonus")
    private Integer maxIncreaseBonus;

    /**
     * 初始设置积分
     */
    @Column(name = "init_increase_bonus")
    private Integer initIncreaseBonus;

    /**
     * 每使用5积分
     */
    @Column(name = "cost_bonus_unit")
    private Integer costBonusUnit;

    /**
     * 抵扣xx元，（这里以分为单位）
     */
    @Column(name = "reduce_money")
    private Integer reduceMoney;

    /**
     * 抵扣条件，满xx元（这里以分为单位）可用
     */
    @Column(name = "least_money_to_use_bonus")
    private Integer leastMoneyToUseBonus;

    /**
     * 抵扣条件，单笔最多使用xx积分
     */
    @Column(name = "max_reduce_bonus")
    private Integer maxReduceBonus;

    /**
     * 是否设置该会员卡支持拉出微信支付刷卡界面
     * 必填：否
     * bool
     */
    @Column(name = "swipe_card")
    private Integer swipeCard;

    /**
     * 是否设置该会员卡中部的按钮同时支持微信支付刷卡和会员卡二维码
     * 必填：否
     * bool
     */
    @Column(name = "pay_and_qrcode")
    private Integer payAndQrcode;

    /**
     * 填写true为用户点击进入会员卡时推送事件，默认为false。详情见 进入会员卡事件推送
     * 必填：否
     * bool
     */
    @Column(name = "need_push_on_view")
    private Integer needPushOnView;

    public Integer getNeedPushOnView() {
        return needPushOnView;
    }

    public void setNeedPushOnView(Integer needPushOnView) {
        this.needPushOnView = needPushOnView;
    }

    public Integer getPayAndQrcode() {
        return payAndQrcode;
    }

    public void setPayAndQrcode(Integer payAndQrcode) {
        this.payAndQrcode = payAndQrcode;
    }

    public Integer getSwipeCard() {
        return swipeCard;
    }

    public void setSwipeCard(Integer swipeCard) {
        this.swipeCard = swipeCard;
    }

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Long getBeginTimestamp() {
        return beginTimestamp;
    }

    public void setBeginTimestamp(Long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Integer getFixedTerm() {
        return fixedTerm;
    }

    public void setFixedTerm(Integer fixedTerm) {
        this.fixedTerm = fixedTerm;
    }

    public Integer getFixedBeginTerm() {
        return fixedBeginTerm;
    }

    public void setFixedBeginTerm(Integer fixedBeginTerm) {
        this.fixedBeginTerm = fixedBeginTerm;
    }

    public Integer getReduceCost() {
        return reduceCost;
    }

    public void setReduceCost(Integer reduceCost) {
        this.reduceCost = reduceCost;
    }

    public Integer getLeastCost() {
        return leastCost;
    }

    public void setLeastCost(Integer leastCost) {
        this.leastCost = leastCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getDefaultDetail() {
        return defaultDetail;
    }

    public void setDefaultDetail(String defaultDetail) {
        this.defaultDetail = defaultDetail;
    }

    public String getDealDetail() {
        return dealDetail;
    }

    public void setDealDetail(String dealDetail) {
        this.dealDetail = dealDetail;
    }

    public Integer getUseCustomCode() {
        return useCustomCode;
    }

    public void setUseCustomCode(Integer useCustomCode) {
        this.useCustomCode = useCustomCode;
    }

    public String getGetCustomCodeMode() {
        return getCustomCodeMode;
    }

    public void setGetCustomCodeMode(String getCustomCodeMode) {
        this.getCustomCodeMode = getCustomCodeMode;
    }

    public Integer getBindOpenid() {
        return bindOpenid;
    }

    public void setBindOpenid(Integer bindOpenid) {
        this.bindOpenid = bindOpenid;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }

    public Integer getUseAllLocation() {
        return useAllLocation;
    }

    public void setUseAllLocation(Integer useAllLocation) {
        this.useAllLocation = useAllLocation;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public String getCenterSubTitle() {
        return centerSubTitle;
    }

    public void setCenterSubTitle(String centerSubTitle) {
        this.centerSubTitle = centerSubTitle;
    }

    public String getCenterUrl() {
        return centerUrl;
    }

    public void setCenterUrl(String centerUrl) {
        this.centerUrl = centerUrl;
    }

    public String getCenterAppBrandUserName() {
        return centerAppBrandUserName;
    }

    public void setCenterAppBrandUserName(String centerAppBrandUserName) {
        this.centerAppBrandUserName = centerAppBrandUserName;
    }

    public String getCenterAppBrandPass() {
        return centerAppBrandPass;
    }

    public void setCenterAppBrandPass(String centerAppBrandPass) {
        this.centerAppBrandPass = centerAppBrandPass;
    }

    public String getCustomUrlName() {
        return customUrlName;
    }

    public void setCustomUrlName(String customUrlName) {
        this.customUrlName = customUrlName;
    }

    public String getCustomUrlSubTitle() {
        return customUrlSubTitle;
    }

    public void setCustomUrlSubTitle(String customUrlSubTitle) {
        this.customUrlSubTitle = customUrlSubTitle;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getCustomAppBrandUserName() {
        return customAppBrandUserName;
    }

    public void setCustomAppBrandUserName(String customAppBrandUserName) {
        this.customAppBrandUserName = customAppBrandUserName;
    }

    public String getCustomAppBrandPass() {
        return customAppBrandPass;
    }

    public void setCustomAppBrandPass(String customAppBrandPass) {
        this.customAppBrandPass = customAppBrandPass;
    }

    public String getPromotionUrlName() {
        return promotionUrlName;
    }

    public void setPromotionUrlName(String promotionUrlName) {
        this.promotionUrlName = promotionUrlName;
    }

    public String getPromotionUrlSubTitle() {
        return promotionUrlSubTitle;
    }

    public void setPromotionUrlSubTitle(String promotionUrlSubTitle) {
        this.promotionUrlSubTitle = promotionUrlSubTitle;
    }

    public String getPromotionUrl() {
        return promotionUrl;
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public String getPromotionAppBrandUserName() {
        return promotionAppBrandUserName;
    }

    public void setPromotionAppBrandUserName(String promotionAppBrandUserName) {
        this.promotionAppBrandUserName = promotionAppBrandUserName;
    }

    public String getPromotionAppBrandPass() {
        return promotionAppBrandPass;
    }

    public void setPromotionAppBrandPass(String promotionAppBrandPass) {
        this.promotionAppBrandPass = promotionAppBrandPass;
    }

    public Integer getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(Integer getLimit) {
        this.getLimit = getLimit;
    }

    public Integer getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(Integer useLimit) {
        this.useLimit = useLimit;
    }

    public Integer getCanShare() {
        return canShare;
    }

    public void setCanShare(Integer canShare) {
        this.canShare = canShare;
    }

    public Integer getCanGiveFriend() {
        return canGiveFriend;
    }

    public void setCanGiveFriend(Integer canGiveFriend) {
        this.canGiveFriend = canGiveFriend;
    }

    public String getObjectUseFor() {
        return objectUseFor;
    }

    public void setObjectUseFor(String objectUseFor) {
        this.objectUseFor = objectUseFor;
    }

    public Integer getCanUseWithOtherDiscount() {
        return canUseWithOtherDiscount;
    }

    public void setCanUseWithOtherDiscount(Integer canUseWithOtherDiscount) {
        this.canUseWithOtherDiscount = canUseWithOtherDiscount;
    }

    public String getCardAbstract() {
        return cardAbstract;
    }

    public void setCardAbstract(String cardAbstract) {
        this.cardAbstract = cardAbstract;
    }

    public String getIconUrlList() {
        return iconUrlList;
    }

    public void setIconUrlList(String iconUrlList) {
        this.iconUrlList = iconUrlList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBusinessService() {
        return businessService;
    }

    public void setBusinessService(String businessService) {
        this.businessService = businessService;
    }

    public String getAdvancedType() {
        return advancedType;
    }

    public void setAdvancedType(String advancedType) {
        this.advancedType = advancedType;
    }

    public Integer getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Integer beginHour) {
        this.beginHour = beginHour;
    }

    public Integer getBeginMinute() {
        return beginMinute;
    }

    public void setBeginMinute(Integer beginMinute) {
        this.beginMinute = beginMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public String getPrerogative() {
        return prerogative;
    }

    public void setPrerogative(String prerogative) {
        this.prerogative = prerogative;
    }

    public Integer getAutoActivate() {
        return autoActivate;
    }

    public void setAutoActivate(Integer autoActivate) {
        this.autoActivate = autoActivate;
    }

    public Integer getSupplyBonus() {
        return supplyBonus;
    }

    public void setSupplyBonus(Integer supplyBonus) {
        this.supplyBonus = supplyBonus;
    }

    public String getBonusUrl() {
        return bonusUrl;
    }

    public void setBonusUrl(String bonusUrl) {
        this.bonusUrl = bonusUrl;
    }

    public Integer getSupplyBalance() {
        return supplyBalance;
    }

    public void setSupplyBalance(Integer supplyBalance) {
        this.supplyBalance = supplyBalance;
    }

    public String getBalanceUrl() {
        return balanceUrl;
    }

    public void setBalanceUrl(String balanceUrl) {
        this.balanceUrl = balanceUrl;
    }

    public String getActivateUrl() {
        return activateUrl;
    }

    public void setActivateUrl(String activateUrl) {
        this.activateUrl = activateUrl;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getBonusCleared() {
        return bonusCleared;
    }

    public void setBonusCleared(String bonusCleared) {
        this.bonusCleared = bonusCleared;
    }

    public String getBonusRules() {
        return bonusRules;
    }

    public void setBonusRules(String bonusRules) {
        this.bonusRules = bonusRules;
    }

    public String getBalanceRules() {
        return balanceRules;
    }

    public void setBalanceRules(String balanceRules) {
        this.balanceRules = balanceRules;
    }

    public String getActivateAppBrandUserName() {
        return activateAppBrandUserName;
    }

    public void setActivateAppBrandUserName(String activateAppBrandUserName) {
        this.activateAppBrandUserName = activateAppBrandUserName;
    }

    public String getActivateAppBrandPass() {
        return activateAppBrandPass;
    }

    public void setActivateAppBrandPass(String activateAppBrandPass) {
        this.activateAppBrandPass = activateAppBrandPass;
    }

    public Integer getWxActivate() {
        return wxActivate;
    }

    public void setWxActivate(Integer wxActivate) {
        this.wxActivate = wxActivate;
    }

    public Integer getCostMoneyUnit() {
        return costMoneyUnit;
    }

    public void setCostMoneyUnit(Integer costMoneyUnit) {
        this.costMoneyUnit = costMoneyUnit;
    }

    public Integer getIncreaseBonus() {
        return increaseBonus;
    }

    public void setIncreaseBonus(Integer increaseBonus) {
        this.increaseBonus = increaseBonus;
    }

    public Integer getMaxIncreaseBonus() {
        return maxIncreaseBonus;
    }

    public void setMaxIncreaseBonus(Integer maxIncreaseBonus) {
        this.maxIncreaseBonus = maxIncreaseBonus;
    }

    public Integer getInitIncreaseBonus() {
        return initIncreaseBonus;
    }

    public void setInitIncreaseBonus(Integer initIncreaseBonus) {
        this.initIncreaseBonus = initIncreaseBonus;
    }

    public Integer getCostBonusUnit() {
        return costBonusUnit;
    }

    public void setCostBonusUnit(Integer costBonusUnit) {
        this.costBonusUnit = costBonusUnit;
    }

    public Integer getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(Integer reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public Integer getLeastMoneyToUseBonus() {
        return leastMoneyToUseBonus;
    }

    public void setLeastMoneyToUseBonus(Integer leastMoneyToUseBonus) {
        this.leastMoneyToUseBonus = leastMoneyToUseBonus;
    }

    public Integer getMaxReduceBonus() {
        return maxReduceBonus;
    }

    public void setMaxReduceBonus(Integer maxReduceBonus) {
        this.maxReduceBonus = maxReduceBonus;
    }

}
