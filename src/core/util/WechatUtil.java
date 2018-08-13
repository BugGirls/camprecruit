package core.util;

import com.google.gson.reflect.TypeToken;
import com.jeefw.model.sys.WechatCard;
import com.jeefw.model.sys.WechatPoi;
import com.jeefw.model.sys.WechatQrcodePuttingCard;
import com.jeefw.model.sys.bo.AccessTokenBo;
import core.enums.CardTypeEnum;
import core.enums.DateInfoType;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 微信对接工具类
 *
 * @author Hystar
 * @date 2018/7/9
 */
@Component
public class WechatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    public static AccessTokenBo accessTokenBo = null;

    /**
     * 验证消息是否来自微信服务器
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] array = new String[]{Const.TOKEN, timestamp, nonce};

        // 1、将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(array);

        // 2、将三个参数字符串拼接成一个字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            content.append(array[i]);
        }

        // 3、进行sha1加密
        String sign = Utils.sha1(content.toString());

        return sign.equals(signature);
    }

    /**
     * 获取 access_token
     * 有效期：7200s
     *
     * @return
     */
    public static AccessTokenBo getAccessToken() {
        String url = Const.ACCESS_TOKEN_URL.replace("APPID", Const.APP_ID).replace("APPSECRET", Const.APP_SECRET);
        String result = HttpUtil.httpsRequest(url, "GET", null);
        System.out.println(GSON.toJson(result));
        if (result != null) {
            accessTokenBo = GSON.toObject(result, AccessTokenBo.class);
        }
        return accessTokenBo;
    }

    /**
     * 创建定时任务
     * 据上次执行时间相隔7000s后再次执行
     */
    @Scheduled(fixedRate = 1000 * 7000)
    public void tokenScheduled() throws InterruptedException {
        accessTokenBo = getAccessToken();
        logger.info("accessToken={}", GSON.toJson(accessTokenBo));
    }

    /**
     * 新增永久素材（视频素材除外）
     *
     * @param filePath
     * @param type
     * @return
     */
    public static Map<String, String> addEverMaterialExcludeVideo(String filePath, String type) throws Exception {
        if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            throw new Exception("未获取到access_token");
        }

        String url = Const.ADD_EVER_MATERIAL_URL.replace("ACCESS_TOKEN", accessTokenBo.getAccess_token()).replace("TYPE", type);
        String result = HttpUtil.httpsPostEverMaterialExcludeVideo(url, filePath);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 删除永久素材
     *
     * @param mediaId
     * @return
     */
    public static Map<String, String> deleteEverMaterial(String mediaId) throws Exception {
        if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            throw new Exception("未获取到access_token");
        }

        String body = "{\"media_id\":\"" + mediaId + "\"}";

        String url = Const.DELETE_EVER_MATERIAL_URL.replace("ACCESS_TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpsRequest(url, "POST", body);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 创建卡券
     *
     * @param wechatCard
     * @return
     */
    public static JSONObject createCard(WechatCard wechatCard) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errcode", -1);

        if (wechatCard == null) {
            resultMap.put("errmsg", "卡券信息为空");
            return JSONObject.fromObject(resultMap);
        } else if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            resultMap.put("errmsg", "没有获取到access_token");
            return JSONObject.fromObject(resultMap);
        } else if (wechatCard.getQuantity() == null && wechatCard.getQuantity() == 0) {
            resultMap.put("errmsg", "库存数量为0");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getDateType())) {
            resultMap.put("errmsg", "使用时间的类型不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getLogoUrl())) {
            resultMap.put("errmsg", "卡券的商户logo必填");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getCodeType())) {
            resultMap.put("errmsg", "卡券码型不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getBrandName())) {
            resultMap.put("errmsg", "商户名称不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getTitle())) {
            resultMap.put("errmsg", "卡券名不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getColor())) {
            resultMap.put("errmsg", "卡券颜色不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getNotice())) {
            resultMap.put("errmsg", "卡券使用提醒不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getDescription())) {
            resultMap.put("errmsg", "卡券描述不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getCardType())) {
            resultMap.put("errmsg", "卡券类型不能为空");
            return JSONObject.fromObject(resultMap);
        }

        Map<String, Object> skuMap = Utils.asHashMap("quantity", wechatCard.getQuantity());
        Map<String, Object> dateInfoMap;
        if (wechatCard.getDateType().equals(DateInfoType.DATE_TYPE_FIX_TIME_RANGE.getKey())) {
            dateInfoMap = Utils.asHashMap("type", wechatCard.getDateType(), "begin_timestamp", wechatCard.getBeginTimestamp(), "end_timestamp", wechatCard.getEndTimestamp());
        } else if (wechatCard.getDateType().equals(DateInfoType.DATE_TYPE_FIX_TERM.getKey())) {
            dateInfoMap = Utils.asHashMap("type", wechatCard.getDateType(), "fixed_term", wechatCard.getFixedTerm(), "fixed_begin_term", wechatCard.getFixedBeginTerm());
        } else {
            resultMap.put("errmsg", "没有找到该时间类型的枚举");
            return JSONObject.fromObject(resultMap);
        }

        // 1、卡券基本信息类型
        Map<String, Object> baseInfoMap = new HashMap<>();
        // base_info必填字段
        baseInfoMap.put("logo_url", wechatCard.getLogoUrl());
        baseInfoMap.put("code_type", wechatCard.getCodeType());
        baseInfoMap.put("brand_name", wechatCard.getBrandName());
        baseInfoMap.put("title", wechatCard.getTitle());
        baseInfoMap.put("color", wechatCard.getColor());
        baseInfoMap.put("notice", wechatCard.getNotice());
        baseInfoMap.put("description", wechatCard.getDescription());
        baseInfoMap.put("date_info", dateInfoMap);
        baseInfoMap.put("sku", skuMap);

        // base_info 非必填字段
        if (wechatCard.getUseCustomCode() != null) {
            baseInfoMap.put("use_custom_code", wechatCard.getUseCustomCode() == 1 ? true : false);
        }
        if (wechatCard.getBindOpenid() != null) {
            baseInfoMap.put("bind_openid", wechatCard.getBindOpenid() == 1 ? true : false);
        }
        if (StringUtils.isNotBlank(wechatCard.getServicePhone())) {
            baseInfoMap.put("service_phone", wechatCard.getServicePhone());
        }
        List<String> poiIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(wechatCard.getLocationIds())) {
            Integer index = wechatCard.getLocationIds().indexOf(",");
            if (index > 0) {
                String[] sp = wechatCard.getLocationIds().split(",");
                for (int i = 0; i < sp.length; i++) {
                    poiIdList.add(sp[i]);
                }
            }
        }
        if (StringUtils.isBlank(wechatCard.getLocationIds()) && wechatCard.getUseAllLocation() != null) {
            baseInfoMap.put("use_all_locations", wechatCard.getUseAllLocation() == 1 ? true : false);
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterTitle())) {
            baseInfoMap.put("center_title", wechatCard.getCenterTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterSubTitle())) {
            baseInfoMap.put("center_sub_title", wechatCard.getCenterSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterUrl())) {
            baseInfoMap.put("center_url", wechatCard.getCenterUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterAppBrandUserName())) {
            baseInfoMap.put("center_app_brand_user_name", wechatCard.getCenterAppBrandUserName());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterAppBrandPass())) {
            baseInfoMap.put("center_app_brand_pass", wechatCard.getCenterAppBrandPass());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrlName())) {
            baseInfoMap.put("custom_url_name", wechatCard.getCustomUrlName());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrlSubTitle())) {
            baseInfoMap.put("custom_url_sub_title", wechatCard.getCustomUrlSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrl())) {
            baseInfoMap.put("custom_url", wechatCard.getCustomUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomAppBrandUserName())) {
            baseInfoMap.put("custom_app_brand_user_name", wechatCard.getCustomAppBrandUserName());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomAppBrandPass())) {
            baseInfoMap.put("custom_app_brand_pass", wechatCard.getCustomAppBrandPass());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrlName())) {
            baseInfoMap.put("promotion_url_name", wechatCard.getPromotionUrlName());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrlSubTitle())) {
            baseInfoMap.put("promotion_url_sub_title", wechatCard.getPromotionUrlSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrl())) {
            baseInfoMap.put("promotion_url", wechatCard.getPromotionUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionAppBrandUserName())) {
            baseInfoMap.put("promotion_app_brand_user_name", wechatCard.getPromotionAppBrandUserName());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionAppBrandPass())) {
            baseInfoMap.put("promotion_app_brand_pass", wechatCard.getPromotionAppBrandPass());
        }
        if (wechatCard.getGetLimit() != null && wechatCard.getGetLimit() != 0) {
            baseInfoMap.put("get_limit", wechatCard.getGetLimit());
        }
        if (wechatCard.getUseLimit() != null && wechatCard.getUseLimit() != 0) {
            baseInfoMap.put("use_limit", wechatCard.getUseLimit());
        }
        if (wechatCard.getCanShare() != null && wechatCard.getCanShare() != 0) {
            baseInfoMap.put("can_share", wechatCard.getCanShare() == 1 ? true : false);
        }
        if (wechatCard.getCanGiveFriend() != null && wechatCard.getCanGiveFriend() != 0) {
            baseInfoMap.put("can_give_friend", wechatCard.getCanGiveFriend() == 1 ? true : false);
        }
        if (poiIdList.size() > 0) {
            baseInfoMap.put("location_id_list", poiIdList);
        }

        // 2、卡券高级信息字段
        List<String> businessServiceList = new ArrayList<>();
        if (StringUtils.isNotBlank(wechatCard.getBusinessService())) {
            Integer index = wechatCard.getLocationIds().indexOf(",");
            if (index > 0) {
                String[] sp = wechatCard.getBusinessService().split(",");
                for (int i = 0; i < sp.length; i++) {
                    businessServiceList.add(sp[i]);
                }
            }
        }
        List<Object> timeLimitList = new ArrayList<>();
        Map<String, Object> timeLimitMap = new HashMap<>();
        if (StringUtils.isNotBlank(wechatCard.getAdvancedType())) {
            timeLimitMap.put("type", wechatCard.getAdvancedType());
        }
        if (wechatCard.getBeginHour() != null) {
            timeLimitMap.put("begin_hour", wechatCard.getBeginHour());
        }
        if (wechatCard.getEndHour() != null) {
            timeLimitMap.put("end_hour", wechatCard.getEndHour());
        }
        if (wechatCard.getBeginMinute() != null) {
            timeLimitMap.put("begin_minute", wechatCard.getBeginMinute());
        }
        if (wechatCard.getEndMinute() != null) {
            timeLimitMap.put("end_minute", wechatCard.getEndMinute());
        }
        if (!timeLimitMap.isEmpty()) {
            timeLimitList.add(timeLimitMap);
        }

        Map<String, Object> advancedInfoMap = new HashMap<>();
        if (businessServiceList.size() > 0) {
            advancedInfoMap.put("business_service", businessServiceList);
        }
        if (timeLimitList.size() > 0) {
            advancedInfoMap.put("time_limit", timeLimitList);
        }

        // 组装信息
        Map<String, Object> cardTypeMap = new HashMap<>();
        cardTypeMap.put("base_info", baseInfoMap);
        cardTypeMap.put("advanced_info", advancedInfoMap);
        // 3、卡券专属类型
        if (wechatCard.getCardType().equals(CardTypeEnum.GROUPON.getKey())) {
            // 团购券
            cardTypeMap.put("deal_detail", wechatCard.getDealDetail());
        } else if (wechatCard.getCardType().equals(CardTypeEnum.CASH.getKey())) {
            // 代金券
            cardTypeMap.put("reduce_cost", wechatCard.getReduceCost());
            cardTypeMap.put("least_cost", wechatCard.getLeastCost());
        } else if (wechatCard.getCardType().equals(CardTypeEnum.DISCOUNT.getKey())) {
            // 折扣券
            cardTypeMap.put("discount", wechatCard.getDiscount());
        } else if (wechatCard.getCardType().equals(CardTypeEnum.GENERAL_COUPON.getKey())) {
            // 优惠券
            cardTypeMap.put("default_detail", wechatCard.getDefaultDetail());
        } else if (wechatCard.getCardType().equals(CardTypeEnum.GIFT.getKey())) {
            // 兑换券
            cardTypeMap.put("gift", wechatCard.getGift());
        } else {
            throw new Exception("没有找到该卡券类型的枚举");
        }

        Map<String, Object> cardMap = new HashMap<>();
        cardMap.put("card_type", wechatCard.getCardType());
        if (wechatCard.getCardType().equals(CardTypeEnum.GROUPON.getKey())) {
            // 团购券
            cardMap.put(CardTypeEnum.GROUPON.getKey().toLowerCase(), cardTypeMap);
        } else if (wechatCard.getCardType().equals(CardTypeEnum.CASH.getKey())) {
            // 代金券
            cardMap.put(CardTypeEnum.CASH.getKey().toLowerCase(), cardTypeMap);
        } else if (wechatCard.getCardType().equals(CardTypeEnum.DISCOUNT.getKey())) {
            // 折扣券
            cardMap.put(CardTypeEnum.DISCOUNT.getKey().toLowerCase(), cardTypeMap);
        } else if (wechatCard.getCardType().equals(CardTypeEnum.GENERAL_COUPON.getKey())) {
            // 优惠券
            cardMap.put(CardTypeEnum.GENERAL_COUPON.getKey().toLowerCase(), cardTypeMap);
        } else if (wechatCard.getCardType().equals(CardTypeEnum.GIFT.getKey())) {
            // 兑换券
            cardMap.put(CardTypeEnum.GIFT.getKey().toLowerCase(), cardTypeMap);
        } else {
            throw new Exception("没有找到该卡券类型的枚举");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("card", cardMap);
        String json = GSON.toJson(paramMap);
        System.out.println(GSON.toJson(json));

        String url = Const.CREATE_CARD_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 创建二维码投放卡券
     *
     * @param wechatQrcodePuttingCard
     * @return
     */
    public static Map<String, String> createQrCodePutCard(WechatQrcodePuttingCard wechatQrcodePuttingCard) throws Exception {
        if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            throw new Exception("未获取到access_token");
        }

        if (wechatQrcodePuttingCard == null) {
            throw new Exception("参数错误");
        }

        String json = null;
        try {
            if (wechatQrcodePuttingCard.getActionName().equals("QR_CARD")) {
                // 设置扫描二维码领取单张卡券
                Map<String, Object> cardMap = new HashMap<>();
                cardMap.put("card_id", wechatQrcodePuttingCard.getWechatCardId());
                if (StringUtils.isNotBlank(wechatQrcodePuttingCard.getCode())) {
                    cardMap.put("code", wechatQrcodePuttingCard.getCode());
                }
                if (StringUtils.isNotBlank(wechatQrcodePuttingCard.getOpenid())) {
                    cardMap.put("openid", wechatQrcodePuttingCard.getOpenid());
                }
                if (wechatQrcodePuttingCard.getUniqueCode() != null) {
                    cardMap.put("is_unique_code", wechatQrcodePuttingCard.getUniqueCode() == 1 ? true : false);
                }
                if (StringUtils.isNotBlank(wechatQrcodePuttingCard.getOuterStr())) {
                    cardMap.put("outer_str", wechatQrcodePuttingCard.getOuterStr());
                }
                if (wechatQrcodePuttingCard.getOuterId() != null) {
                    cardMap.put("outer_id", wechatQrcodePuttingCard.getOuterId());
                }

                Map<String, Object> actionInfoMap = new HashMap<>();
                actionInfoMap.put("card", cardMap);

                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("action_name", wechatQrcodePuttingCard.getActionName());
                if (wechatQrcodePuttingCard.getExpireSeconds() != null) {
                    if (wechatQrcodePuttingCard.getExpireSeconds() < 60 || wechatQrcodePuttingCard.getExpireSeconds() > 1800) {
                        throw new Exception("二维码的有效时间有误，范围是60 ~ 1800秒。不填默认为365天有效");
                    } else {
                        paramMap.put("expire_seconds", wechatQrcodePuttingCard.getExpireSeconds());
                    }
                }
                paramMap.put("action_info", actionInfoMap);

                json = GSON.toJson(paramMap);
            } else if (wechatQrcodePuttingCard.getActionName().equals("QR_MULTIPLE_CARD")) {

            } else {
                throw new Exception("卡券投放类型错误");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        if (StringUtils.isNotBlank(json)) {
            String url = Const.PUT_CARD_QRCODE_URL.replace("TOKEN", accessTokenBo.getAccess_token());
            String result = HttpUtil.httpsRequest(url, "POST", json);
            return GSON.toObject(result, new TypeToken<Map<String, String>>() {
            }.getType());
        } else {
            logger.error(json);
            return null;
        }
    }

    /**
     * 查询code接口
     *
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject getCardForCode(String code) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();

        if (StringUtils.isBlank(code)) {
            throw new Exception("code为空");
        }

        paramMap.put("code", code);
        paramMap.put("check_consume", true);

        String json = GSON.toJson(paramMap);
        String url = Const.GET_CODE_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 核销卡券
     *
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject consumeCard(String code) throws Exception {
        if (StringUtils.isBlank(code)) {
            throw new Exception("code为空");
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", code);

        String json = GSON.toJson(paramMap);
        String url = Const.CONSUME_CARD_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 删除卡券
     *
     * @param cardId
     * @return
     */
    public static Map<String, String> deleteCard(String cardId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("card_id", cardId);

        String json = GSON.toJson(paramMap);
        String url = Const.DELETE_CARD_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpsRequest(url, "POST", json);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 设置卡券失效
     *
     * @param cardId 必填，卡券ID
     * @param code   必填，设置失效的Code码
     * @param reason 选填，失效理由
     * @return
     */
    public static JSONObject setCardUnavailable(String cardId, String code, String reason) throws Exception {
        if (StringUtils.isBlank(cardId) || StringUtils.isBlank(code)) {
            throw new Exception("参数错误");
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("card_id", cardId);
        paramMap.put("code", code);
        if (StringUtils.isNotBlank(reason)) {
            paramMap.put("reason", reason);
        }

        String json = GSON.toJson(paramMap);
        String url = Const.SET_CARD_UNAVAILABLE.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 更改Code
     *
     * @param cardId  选填，卡券ID
     * @param code    必填，需变更的Code码
     * @param newCode 必填，变更后的有效Code码
     * @return
     */
    public static JSONObject updateCode(String cardId, String code, String newCode) throws Exception {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(newCode)) {
            throw new Exception("参数错误");
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("code", code);
        paramMap.put("new_code", newCode);
        if (StringUtils.isNotBlank(cardId)) {
            paramMap.put("card_id", cardId);
        }

        String json = GSON.toJson(paramMap);
        String url = Const.UPDATE_CODE_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 修改卡券库存
     *
     * @param cardId             必填，卡券ID
     * @param increaseStockValue 增加多少库存，支持不填或填0
     * @param reduceStockValue   减少多少库存，可以不填或填0
     * @return
     */
    public static JSONObject modifyStock(String cardId, Integer increaseStockValue, Integer reduceStockValue) throws Exception {
        if (StringUtils.isBlank(cardId)) {
            return null;
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("card_id", cardId);
        if (increaseStockValue != null) {
            paramMap.put("increase_stock_value", increaseStockValue);
        }
        if (reduceStockValue != null) {
            paramMap.put("reduce_stock_value", reduceStockValue);
        }

        String json = GSON.toJson(paramMap);
        String url = Const.MODIFY_STOCK_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 创建会员卡
     * 用户领取卡片之后，如果系统自动帮用户激活，积分、储值等自定义显示信息均为0，开发者可以通过更新会员信息接口更新用户会员数据
     *
     * @param wechatCard
     * @return
     * @throws Exception
     */
    public static JSONObject createMemberCard(WechatCard wechatCard) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errcode", -1);

        if (wechatCard == null) {
            resultMap.put("errmsg", "卡券信息为空");
            return JSONObject.fromObject(resultMap);
        } else if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            resultMap.put("errmsg", "没有获取到access_token");
            return JSONObject.fromObject(resultMap);
        } else if (wechatCard.getQuantity() == null && wechatCard.getQuantity() == 0) {
            resultMap.put("errmsg", "库存数量为0");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getDateType())) {
            resultMap.put("errmsg", "使用时间的类型不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getLogoUrl())) {
            resultMap.put("errmsg", "卡券的商户logo必填");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getCodeType())) {
            resultMap.put("errmsg", "卡券码型不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getBrandName())) {
            resultMap.put("errmsg", "商户名称不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getTitle())) {
            resultMap.put("errmsg", "卡券名不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getColor())) {
            resultMap.put("errmsg", "卡券颜色不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getNotice())) {
            resultMap.put("errmsg", "卡券使用提醒不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getDescription())) {
            resultMap.put("errmsg", "卡券描述不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getCardType())) {
            resultMap.put("errmsg", "卡券类型不能为空");
            return JSONObject.fromObject(resultMap);
        } else if (wechatCard.getSupplyBonus() == null) {
            resultMap.put("errmsg", "请判断是否显示积分");
            return JSONObject.fromObject(resultMap);
        } else if (wechatCard.getSupplyBalance() == null) {
            resultMap.put("errmsg", "请判断是否支持储值");
            return JSONObject.fromObject(resultMap);
        } else if (StringUtils.isBlank(wechatCard.getPrerogative())) {
            resultMap.put("errmsg", "请输入会员卡特权说明");
            return JSONObject.fromObject(resultMap);
        }

        Map<String, String> skuMap = Utils.asHashMap("quantity", wechatCard.getQuantity());
        Map<String, String> dateInfoMap = new HashMap<>();
        if (wechatCard.getDateType().equals(DateInfoType.DATE_TYPE_FIX_TIME_RANGE.getKey())) {
            dateInfoMap = Utils.asHashMap("type", wechatCard.getDateType(), "begin_timestamp", wechatCard.getBeginTimestamp(), "end_timestamp", wechatCard.getEndTimestamp());
        } else if (wechatCard.getDateType().equals(DateInfoType.DATE_TYPE_FIX_TERM.getKey())) {
            dateInfoMap = Utils.asHashMap("type", wechatCard.getDateType(), "fixed_term", wechatCard.getFixedTerm(), "fixed_begin_term", wechatCard.getFixedBeginTerm());
        } else if (wechatCard.getDateType().equals(DateInfoType.DATE_TYPE_PERMANENT.getKey())) {
            dateInfoMap = Utils.asHashMap("type", DateInfoType.DATE_TYPE_PERMANENT.getKey());
        } else {
            resultMap.put("errmsg", "没有找到时间类型的枚举");
            return JSONObject.fromObject(dateInfoMap);
        }

        // 1、卡券基本信息类型
        Map<String, Object> baseInfoMap = new HashMap<>();
        // base_info必填字段
        baseInfoMap.put("logo_url", wechatCard.getLogoUrl());
        baseInfoMap.put("brand_name", wechatCard.getBrandName());
        baseInfoMap.put("code_type", wechatCard.getCodeType());
        baseInfoMap.put("title", wechatCard.getTitle());
        baseInfoMap.put("color", wechatCard.getColor());
        baseInfoMap.put("notice", wechatCard.getNotice());
        baseInfoMap.put("description", wechatCard.getDescription());
        baseInfoMap.put("date_info", dateInfoMap);
        baseInfoMap.put("sku", skuMap);

        Map<String, Object> swipeCardMap = new HashMap<>();
        if (wechatCard.getSwipeCard() != null) {
            swipeCardMap.put("is_swipe_card", wechatCard.getSwipeCard() == 1 ? true : false);
        }
        Map<String, Object> payInfoMap = new HashMap<>();
        if (!swipeCardMap.isEmpty()) {
            payInfoMap.put("swipe_card", swipeCardMap);
        }

        List<String> poiIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(wechatCard.getLocationIds())) {
            Integer index = wechatCard.getLocationIds().indexOf(",");
            if (index > 0) {
                String[] sp = wechatCard.getLocationIds().split(",");
                for (int i = 0; i < sp.length; i++) {
                    poiIdList.add(sp[i]);
                }
            }
        }

        // base_info 非必填字段
        if (wechatCard.getPayAndQrcode() != null) {
            baseInfoMap.put("is_pay_and_qrcode", wechatCard.getPayAndQrcode() == 1 ? true : false);
        }
        if (wechatCard.getUseCustomCode() != null) {
            baseInfoMap.put("use_custom_code", wechatCard.getUseCustomCode() == 1 ? true : false);
        }
        if (wechatCard.getBindOpenid() != null) {
            baseInfoMap.put("bind_openid", wechatCard.getBindOpenid() == 1 ? true : false);
        }
        if (wechatCard.getUseAllLocation() != null) {
            baseInfoMap.put("use_all_locations", true);
        }
        if (StringUtils.isNotBlank(wechatCard.getServicePhone())) {
            baseInfoMap.put("service_phone", wechatCard.getServicePhone());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterTitle())) {
            baseInfoMap.put("center_title", wechatCard.getCenterTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterSubTitle())) {
            baseInfoMap.put("center_sub_title", wechatCard.getCenterSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCenterUrl())) {
            baseInfoMap.put("center_url", wechatCard.getCenterUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrlName())) {
            baseInfoMap.put("custom_url_name", wechatCard.getCustomUrlName());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrlSubTitle())) {
            baseInfoMap.put("custom_url_sub_title", wechatCard.getCustomUrlSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getCustomUrl())) {
            baseInfoMap.put("custom_url", wechatCard.getCustomUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrlName())) {
            baseInfoMap.put("promotion_url_name", wechatCard.getPromotionUrlName());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrlSubTitle())) {
            baseInfoMap.put("promotion_url_sub_title", wechatCard.getPromotionUrlSubTitle());
        }
        if (StringUtils.isNotBlank(wechatCard.getPromotionUrl())) {
            baseInfoMap.put("promotion_url", wechatCard.getPromotionUrl());
        }
        if (wechatCard.getGetLimit() != null && wechatCard.getGetLimit() != 0) {
            baseInfoMap.put("get_limit", wechatCard.getGetLimit());
        }
        if (wechatCard.getCanShare() != null) {
            baseInfoMap.put("can_share", wechatCard.getCanShare() == 1 ? true : false);
        }
        if (wechatCard.getCanGiveFriend() != null) {
            baseInfoMap.put("can_give_friend", wechatCard.getCanGiveFriend() == 1 ? true : false);
        }
        if (wechatCard.getNeedPushOnView() != null) {
            baseInfoMap.put("need_push_on_view", wechatCard.getNeedPushOnView() == 1 ? true : false);
        }
        if (!payInfoMap.isEmpty()) {
            baseInfoMap.put("pay_info", payInfoMap);
        }
        if (poiIdList.size() > 0) {
            baseInfoMap.put("location_id_list", poiIdList);
        }

        // 2、卡券高级信息字段
        Map<String, Object> abstractMap = new HashMap<>();
        if (StringUtils.isNotBlank(wechatCard.getCardAbstract())) {
            abstractMap.put("abstract", wechatCard.getCardAbstract());
        }
        if (StringUtils.isNotBlank(wechatCard.getIconUrlList())) {
            List<String> iconUrlList = new ArrayList<>();
            iconUrlList.add(wechatCard.getIconUrlList());
            abstractMap.put("icon_url_list", iconUrlList);
        }

        List<Object> textImageList = new ArrayList<>();
        Map<String, String> textImageMap = new HashMap<>();
        if (StringUtils.isNotBlank(wechatCard.getImageUrl())) {
            textImageMap.put("image_url", wechatCard.getImageUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getText())) {
            textImageMap.put("text", wechatCard.getText());
        }
        if (!textImageMap.isEmpty()) {
            textImageList.add(textImageMap);
        }

        List<Object> timeLimitList = new ArrayList<>();
        Map<String, Object> timeLimitMap = new HashMap<>();
        if (StringUtils.isNotBlank(wechatCard.getAdvancedType())) {
            timeLimitMap.put("type", wechatCard.getAdvancedType());
        }
        if (wechatCard.getBeginHour() != null && wechatCard.getBeginHour() != 0) {
            timeLimitMap.put("begin_hour", wechatCard.getBeginHour());
        }
        if (wechatCard.getEndHour() != null && wechatCard.getEndHour() != 0) {
            timeLimitMap.put("end_hour", wechatCard.getEndHour());
        }
        if (wechatCard.getBeginMinute() != null && wechatCard.getBeginMinute() != 0) {
            timeLimitMap.put("begin_minute", wechatCard.getBeginMinute());
        }
        if (wechatCard.getEndMinute() != null && wechatCard.getEndMinute() != 0) {
            timeLimitMap.put("end_minute", wechatCard.getEndMinute());
        }
        if (!timeLimitMap.isEmpty()) {
            timeLimitList.add(timeLimitMap);
        }

        List<String> businessServiceList = new ArrayList<>();
        if (StringUtils.isNotBlank(wechatCard.getBusinessService())) {
            Integer index = wechatCard.getBusinessService().indexOf(",");
            if (index > 0) {
                String[] sp = wechatCard.getBusinessService().split(",");
                for (int i = 0; i < sp.length; i++) {
                    businessServiceList.add(sp[i]);
                }
            }
        }

        Map<String, Object> advancedInfoMap = new HashMap<>();
        if (!abstractMap.isEmpty()) {
            advancedInfoMap.put("abstract", abstractMap);
        }
        if (textImageList.size() > 0) {
            advancedInfoMap.put("text_image_list", textImageList);
        }
        if (timeLimitList.size() > 0) {
            advancedInfoMap.put("time_limit", timeLimitList);
        }
        if (businessServiceList.size() > 0) {
            advancedInfoMap.put("business_service", businessServiceList);
        }

        Map<String, String> customField1Map = new HashMap<>();
        if (StringUtils.isNotBlank(wechatCard.getNameType())) {
            customField1Map.put("name_type", wechatCard.getNameType());
        }
        if (StringUtils.isNotBlank(wechatCard.getName())) {
            customField1Map.put("name", wechatCard.getName());
        }
        if (StringUtils.isNotBlank(wechatCard.getUrlType())) {
            customField1Map.put("url", wechatCard.getUrlType());
        }

        Map<String, Object> bonusRuleMap = new HashMap<>();
        if (wechatCard.getCostMoneyUnit() != null && wechatCard.getCostMoneyUnit() != 0) {
            bonusRuleMap.put("cost_money_unit", BigDecimalUtil.mul(wechatCard.getCostMoneyUnit().doubleValue(), new Double(100).doubleValue()));
        }
        if (wechatCard.getIncreaseBonus() != null && wechatCard.getIncreaseBonus() != 0) {
            bonusRuleMap.put("increase_bonus", wechatCard.getIncreaseBonus());
        }
        if (wechatCard.getMaxIncreaseBonus() != null && wechatCard.getMaxIncreaseBonus() != 0) {
            bonusRuleMap.put("max_increase_bonus", wechatCard.getMaxIncreaseBonus());
        }
        if (wechatCard.getInitIncreaseBonus() != null && wechatCard.getInitIncreaseBonus() != 0) {
            bonusRuleMap.put("init_increase_bonus", wechatCard.getInitIncreaseBonus());
        }
        if (wechatCard.getCostBonusUnit() != null && wechatCard.getCostBonusUnit() != 0) {
            bonusRuleMap.put("cost_bonus_unit", wechatCard.getCostBonusUnit());
        }
        if (wechatCard.getReduceMoney() != null && wechatCard.getReduceMoney() != 0) {
            bonusRuleMap.put("reduce_money", BigDecimalUtil.mul(wechatCard.getReduceMoney().doubleValue(), new Double(100).doubleValue()));
        }
        if (wechatCard.getLeastMoneyToUseBonus() != null && wechatCard.getLeastMoneyToUseBonus() != 0) {
            bonusRuleMap.put("least_money_to_use_bonus", BigDecimalUtil.mul(wechatCard.getLeastMoneyToUseBonus().doubleValue(), new Double(100).doubleValue()));
        }
        if (wechatCard.getMaxReduceBonus() != null && wechatCard.getMaxReduceBonus() != 0) {
            bonusRuleMap.put("max_reduce_bonus", wechatCard.getMaxReduceBonus());
        }

        Map<String, Object> cardTypeMap = new HashMap<>();
        cardTypeMap.put("base_info", baseInfoMap);
        cardTypeMap.put("advanced_info", advancedInfoMap);
        cardTypeMap.put("prerogative", wechatCard.getPrerogative());
        cardTypeMap.put("supply_bonus", wechatCard.getSupplyBonus() == 1 ? true : false);
        cardTypeMap.put("supply_balance", wechatCard.getSupplyBalance() == 1 ? true : false);

        if (StringUtils.isNotBlank(wechatCard.getBackgroundPicUrl())) {
            cardTypeMap.put("background_pic_url", wechatCard.getBackgroundPicUrl());
        }
        if (wechatCard.getAutoActivate() != null) {
            cardTypeMap.put("auto_activate", wechatCard.getAutoActivate() == 1 ? true : false);
        }
        if (StringUtils.isNotBlank(wechatCard.getBonusUrl())) {
            cardTypeMap.put("bonus_url", wechatCard.getBonusUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getBalanceUrl())) {
            cardTypeMap.put("balance_url", wechatCard.getBalanceUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getActivateUrl())) {
            cardTypeMap.put("activate_url", wechatCard.getActivateUrl());
        }
        if (StringUtils.isNotBlank(wechatCard.getBonusCleared())) {
            cardTypeMap.put("bonus_cleared", wechatCard.getBonusCleared());
        }
        if (StringUtils.isNotBlank(wechatCard.getBonusRules())) {
            cardTypeMap.put("bonus_rules", wechatCard.getBonusRules());
        }
        if (StringUtils.isNotBlank(wechatCard.getBalanceRules())) {
            cardTypeMap.put("balance_rules", wechatCard.getBalanceRules());
        }
        if (wechatCard.getWxActivate() != null) {
            cardTypeMap.put("wx_activate", wechatCard.getWxActivate() == 1 ? true : false);
        }
        if (wechatCard.getDiscount() != null) {
            cardTypeMap.put("discount", wechatCard.getDiscount());
        }
        if (!customField1Map.isEmpty()) {
            cardTypeMap.put("custom_field1", customField1Map);
        }
        if (!bonusRuleMap.isEmpty()) {
            cardTypeMap.put("bonus_rule", bonusRuleMap);
        }

        Map<String, Object> cardMap = new HashMap<>();
        cardMap.put("card_type", CardTypeEnum.MEMBER_CARD.getKey());
        cardMap.put("member_card", cardTypeMap);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("card", cardMap);

        String json = GSON.toJson(paramMap);
        System.out.println(json);
        String url = Const.CREATE_MEMBER_CARD_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "POST", json);
        return jsonObject;
    }

    /**
     * 上传门店专用的图片
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static Map<String, String> uploadImageForPoi(String filePath) throws Exception {
        if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            throw new Exception("未获取到access_token");
        }

        String url = Const.UPLOAD_IMAGE_FOR_POI_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpsPostEverMaterialExcludeVideo(url, filePath);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 创建门店
     *
     * @param wechatPoi
     * @return
     */
    public static Map<String, String> createPoi(WechatPoi wechatPoi) throws Exception {
        if (accessTokenBo == null || accessTokenBo.getAccess_token() == null) {
            throw new Exception("未获取到access_token");
        }

        List<String> categorieList = new ArrayList<>();
        categorieList.add(wechatPoi.getCategories());

        List<Object> photoList = new ArrayList<>();
        Map<String, String> photoMap = new IdentityHashMap<>();
        if (StringUtils.isNotBlank(wechatPoi.getPhotoUrls())) {
            if (wechatPoi.getPhotoUrls().indexOf(",") > 0) {
                String[] sp = wechatPoi.getPhotoUrls().split(",");
                for (int i = 0; i < sp.length; i++) {
                    photoMap.put("photo_url", sp[i]);
                }
                photoList.add(photoMap);
            } else {
                photoMap.put("photo_url", wechatPoi.getPhotoUrls());
                photoList.add(photoMap);
            }
        }

        Map<String, Object> baseInfoMap = new HashMap<>();
        baseInfoMap.put("sid", wechatPoi.getSid());
        baseInfoMap.put("business_name", wechatPoi.getBusinessName());
        baseInfoMap.put("branch_name", wechatPoi.getBranchName());
        baseInfoMap.put("province", wechatPoi.getProvince());
        baseInfoMap.put("city", wechatPoi.getCity());
        baseInfoMap.put("district", wechatPoi.getDistrict());
        baseInfoMap.put("address", wechatPoi.getAddress());
        baseInfoMap.put("telephone", wechatPoi.getTelephone());
        baseInfoMap.put("categories", categorieList);
        baseInfoMap.put("offset_type", wechatPoi.getOffsetType());
        baseInfoMap.put("longitude", wechatPoi.getLongitude());
        baseInfoMap.put("latitude", wechatPoi.getLatitude());
        if (photoList != null && photoList.size() > 0) {
            baseInfoMap.put("photo_list", photoList);
        }
        if (StringUtils.isNotBlank(wechatPoi.getRecommend())) {
            baseInfoMap.put("recommend", wechatPoi.getRecommend());
        }
        if (StringUtils.isNotBlank(wechatPoi.getSpecial())) {
            baseInfoMap.put("special", wechatPoi.getSpecial());
        }
        if (StringUtils.isNotBlank(wechatPoi.getIntroduction())) {
            baseInfoMap.put("introduction", wechatPoi.getIntroduction());
        }
        if (StringUtils.isNotBlank(wechatPoi.getOpenTime())) {
            baseInfoMap.put("open_time", wechatPoi.getOpenTime());
        }
        if (wechatPoi.getAvgPrice() != null) {
            baseInfoMap.put("avg_price", wechatPoi.getAvgPrice());
        }

        Map<String, Object> businessMap = new HashMap<>();
        businessMap.put("base_info", baseInfoMap);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("business", businessMap);

        String json = GSON.toJson(paramMap);
        String url = Const.CREATE_POI_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpPost(json, url);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 修改门店信息
     *
     * @param wechatPoi
     * @return
     */
    public static Map<String, String> updatePoi(WechatPoi wechatPoi) {
        List<Object> photoList = new ArrayList<>();
        Map<String, String> photoMap = new IdentityHashMap<>();
        if (StringUtils.isNotBlank(wechatPoi.getPhotoUrls())) {
            if (wechatPoi.getPhotoUrls().indexOf(",") > 0) {
                String[] sp = wechatPoi.getPhotoUrls().split(",");
                for (int i = 0; i < sp.length; i++) {
                    photoMap.put("photo_url", sp[i]);
                }
                photoList.add(photoMap);
            } else {
                photoMap.put("photo_url", wechatPoi.getPhotoUrls());
                photoList.add(photoMap);
            }
        }

        Map<String, Object> baseInfoMap = new HashMap<>();
        baseInfoMap.put("poi_id", wechatPoi.getPoiId());
        baseInfoMap.put("sid", wechatPoi.getSid());
        baseInfoMap.put("telephone", wechatPoi.getTelephone());
        baseInfoMap.put("photo_list", photoList);
        baseInfoMap.put("recommend", wechatPoi.getRecommend());
        baseInfoMap.put("special", wechatPoi.getSpecial());
        baseInfoMap.put("introduction", wechatPoi.getIntroduction());
        baseInfoMap.put("open_time", wechatPoi.getOpenTime());
        baseInfoMap.put("avg_price", wechatPoi.getAvgPrice());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("business", baseInfoMap);

        String json = GSON.toJson(paramMap);
        String url = Const.UPDATE_POI_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpPost(json, url);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 删除门店信息
     *
     * @param poiId
     * @return
     */
    public static Map<String, String> deletePoi(String poiId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("poi_id", poiId);

        String json = GSON.toJson(paramMap);
        String url = Const.DELETE_POI_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpPost(json, url);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 查询门店信息
     *
     * @param poiId
     * @return
     */
    public static Map<String, String> getPoi(String poiId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("poi_id", poiId);

        String json = GSON.toJson(paramMap);
        String url = Const.QUERY_POI_URL.replace("TOKEN", accessTokenBo.getAccess_token());
        String result = HttpUtil.httpPost(json, url);
        return GSON.toObject(result, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 获取用户基本信息
     *
     * @param openid
     * @return
     */
    public static JSONObject getUserInfo(String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errcode", -1);

        if (StringUtils.isBlank(openid)) {
            resultMap.put("errmsg", "openid为空");
            return JSONObject.fromObject(resultMap);
        }

        String url = Const.GET_USER_INFO.replace("TOKEN", accessTokenBo.getAccess_token()).replace("OPENID", openid);
        JSONObject jsonObject = HttpUtil.httpsRequest2(url, "GET", null);
        return jsonObject;
    }

}
