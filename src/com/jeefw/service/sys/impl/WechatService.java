package com.jeefw.service.sys.impl;

import com.jeefw.model.sys.*;
import com.jeefw.service.sys.*;
import core.enums.AvailableStatusEnum;
import core.enums.PoiUpdateStatusEnum;
import core.enums.UserCardStatusEnum;
import core.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Hystar
 * @date 2018/7/23
 */
@Service
public class WechatService {

    @Resource
    private WechatCardService wechatCardService;

    @Resource
    private WechatUserGetCardService wechatUserGetCardService;

    @Resource
    private WechatUserConsumeCardService wechatUserConsumeCardService;

    @Resource
    private WechatPoiService wechatPoiService;

    @Resource
    private WechatMemberStoresService wechatMemberStoresService;

    /**
     * 对request数据进行处理
     *
     * @param request
     * @return
     */
    public synchronized String processRequest(HttpServletRequest request) throws Exception {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
        System.out.println(GSON.toJson(map));

        // 发送发账号（一个openid）
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 消息创建时间 （整型）
        String createTime = map.get("CreateTime");

        // 默认回复一个"success"
        String responseMessage = "success";

        // 对消息进行处理
        if (WechatMessageUtil.MESSAGE_EVENT.equals(msgType)) {
            // 事件推送消息
            String event = map.get("Event");

            if (WechatMessageUtil.MESSAGE_EVENT_USER_GET_CARD.equals(event)) {
                // 领取卡券事件推送
                String cardId = map.get("CardId");
                String isGiveByFriend = map.get("IsGiveByFriend");
                String friendUserName = map.get("FriendUserName");
                String oldUserCardCode = map.get("OldUserCardCode");
                String userCardCode = map.get("UserCardCode");
                String outerStr = map.get("OuterStr");
                String isRestoreMemberCard = map.get("IsRestoreMemberCard");
                String unionId = map.get("UnionId");

                // 1、保存到本地数据库
                WechatUserGetCard wechatUserGetCard = new WechatUserGetCard();
                wechatUserGetCard.setCardStatus(UserCardStatusEnum.NORMAL.getKey());
                wechatUserGetCard.setId(KeyUtil.generatorUniqueKey());
                wechatUserGetCard.setCardId(cardId);
                wechatUserGetCard.setCreateTime(WechatMessageUtil.strToDateLong(WechatMessageUtil.formatTime(createTime)));
                wechatUserGetCard.setFriendUserName(friendUserName);
                wechatUserGetCard.setGiveByFriend(Integer.valueOf(isGiveByFriend));
                wechatUserGetCard.setOldUserCardCode(oldUserCardCode);
                wechatUserGetCard.setOpenid(fromUserName);
                wechatUserGetCard.setOuterStr(outerStr);
                wechatUserGetCard.setRestoreMemberCard(Integer.valueOf(isRestoreMemberCard));
                wechatUserGetCard.setUnionId(unionId);
                wechatUserGetCard.setUserCardCode(userCardCode);
                System.out.println(GSON.toJson(wechatUserGetCard));
                wechatUserGetCardService.persist(wechatUserGetCard);

                // 2、减少库存
                WechatCard wechatCard = wechatCardService.getByProerties("cardId", cardId);
                wechatCard.setQuantity(wechatCard.getQuantity() - 1);
                wechatCardService.update(wechatCard);

                // todo 3、使用Java推送服务器推送消息
            } else if (WechatMessageUtil.MESSAGE_EVENT_USER_DEL_CARD.equals(event)) {
                // 用户删除卡券事件推送
                String cardId = map.get("CardId");
                String userCardCode = map.get("UserCardCode");

                // 1、修改用户领取的卡券状态 - 已删除
                String[] propName = {"cardId", "userCardCode"};
                String[] propValue = {cardId, userCardCode};
                WechatUserGetCard wechatUserGetCard = wechatUserGetCardService.getByProerties(propName, propValue);
                if (UserCardStatusEnum.NORMAL.getKey().equals(wechatUserGetCard.getCardStatus())) {
                    wechatUserGetCard.setCardStatus(UserCardStatusEnum.DELETE.getKey());
                    wechatUserGetCardService.update(wechatUserGetCard);
                }
            } else if (WechatMessageUtil.MESSAGE_EVENT_USER_CONSUME_CARD.equals(event)) {
                // 核销事件推送
                String cardId = map.get("CardId");
                String userCardCode = map.get("UserCardCode");
                String consumeSource = map.get("ConsumeSource");
                String locationName = map.get("LocationName");
                String locationId = map.get("LocationId");
                String staffOpenId = map.get("StaffOpenId");
                String verifyCode = map.get("VerifyCode");
                String remarkAmount = map.get("RemarkAmount");
                String outerStr = map.get("OuterStr");

                String[] propName = {"cardId", "userCardCode"};
                String[] propValue = {cardId, userCardCode};
                WechatUserConsumeCard wechatUserConsumeCard = wechatUserConsumeCardService.getByProerties(propName, propValue);
                if (wechatUserConsumeCard == null) {
                    // 1、保存到本地数据库
                    wechatUserConsumeCard = new WechatUserConsumeCard();
                    wechatUserConsumeCard.setId(KeyUtil.generatorUniqueKey());
                    wechatUserConsumeCard.setUserCardCode(userCardCode);
                    wechatUserConsumeCard.setOuterStr(outerStr);
                    wechatUserConsumeCard.setCardId(cardId);
                    wechatUserConsumeCard.setConsumeSource(consumeSource);
                    wechatUserConsumeCard.setCreateTime(WechatMessageUtil.strToDateLong(WechatMessageUtil.formatTime(createTime)));
                    wechatUserConsumeCard.setLocationId(StringUtils.isBlank(locationId) ? null : Integer.valueOf(locationId));
                    wechatUserConsumeCard.setLocationName(locationName);
                    wechatUserConsumeCard.setOpenid(fromUserName);
                    wechatUserConsumeCard.setRemarkAmount(StringUtils.isBlank(remarkAmount) ? null : new BigDecimal(remarkAmount));
                    wechatUserConsumeCard.setStaffOpenId(staffOpenId);
                    wechatUserConsumeCard.setVerifyCode(verifyCode);
                    wechatUserConsumeCardService.persist(wechatUserConsumeCard);
                }

                // 2、修改用户领取的卡券状态 - 已核销
                propName = new String[]{"cardId", "userCardCode"};
                propValue = new String[]{cardId, userCardCode};
                WechatUserGetCard wechatUserGetCard = wechatUserGetCardService.getByProerties(propName, propValue);
                wechatUserGetCard.setCardStatus(UserCardStatusEnum.CONSUMED.getKey());
                wechatUserGetCardService.update(wechatUserGetCard);
            } else if (WechatMessageUtil.MESSAGE_EVENT_POI_CHECK_NOTIFY.equals(event)) {
                System.out.println("门店审核结果的事件推送");
                // 门店审核结果的事件推送
                String uniqId = map.get("UniqId");
                String poiId = map.get("PoiId");
                String result = map.get("Result");
                String msg = map.get("msg");

                WechatPoi wechatPoi = wechatPoiService.getByProerties("sid", uniqId);
                if (wechatPoi != null) {
                    wechatPoi.setPoiId(poiId);
                    wechatPoi.setCreateTime(WechatMessageUtil.strToDateLong(WechatMessageUtil.formatTime(createTime)));
                    wechatPoi.setResult(result);
                    wechatPoi.setMsg(msg);
                    wechatPoi.setUpdateStatus(PoiUpdateStatusEnum.NOT_UPDATE.getCode());
                    if ("succ".equals(result)) {
                        wechatPoi.setAvailableState(AvailableStatusEnum.AVAILABLE_SUCCESS.getCode());

                        // 查询门店信息，更新到本地数据库
                        Map<String, String> resultMap = WechatUtil.getPoi(poiId);
                        System.out.println("获取门店信息：" + GSON.toJson(resultMap));
                        if (("0").equals(resultMap.get("errcode")) && ("ok").equals(resultMap.get("errmsg"))) {
                            wechatPoi.setTelephone(map.get("telephone"));
                            // todo photoUrls
//                            wechatPoi.setPhotoUrls(map.get("telephone"));
                            wechatPoi.setRecommend(map.get("recommend"));
                            wechatPoi.setIntroduction(map.get("introduction"));
                            wechatPoi.setOpenTime(map.get("open_time"));
                            wechatPoi.setAvgPrice(Integer.valueOf(map.get("avg_price")));
                        }
                    } else {
                        wechatPoi.setAvailableState(AvailableStatusEnum.AVAILABLE_FAIL.getCode());
                    }
                    wechatPoiService.update(wechatPoi);
                } else {
                    throw new Exception("该门店信息不存在");
                }
            } else if (WechatMessageUtil.MESSAGE_EVENT_USER_PAY_FROM_PAY_CELL.equals(event)) {
                // 微信买单事件推送
                String cardId = map.get("CardId");
                String code = map.get("UserCardCode");
                String transId = map.get("TransId");
                Integer fee = Integer.valueOf(map.get("Fee"));
                Integer originalFee = Integer.valueOf(map.get("OriginalFee"));

                // 设置积分，通过openid获取会员信息，如果是会员，然后通过cardId获取会员卡信息，设置对应的积分


            } else if (WechatMessageUtil.MESSAGE_EVENT_SUBSCRIBE.equals(event)) {
                // 关注公众号事件推送

                WechatMemberStores wechatMemberStores = wechatMemberStoresService.getByProerties("openid", fromUserName);
                if (wechatMemberStores == null) {
                    // 获取用户信息
                    JSONObject jsonObject = WechatUtil.getUserInfo(fromUserName);

                    wechatMemberStores = new WechatMemberStores();
                    wechatMemberStores.setMemberId(KeyUtil.generatorUniqueKey());
                    wechatMemberStores.setAppId(Const.APP_ID);
                    wechatMemberStores.setSubscribe(jsonObject.getInt("subscribe"));
                    wechatMemberStores.setOpenid(jsonObject.getString("openid"));
                    wechatMemberStores.setNickname(jsonObject.getString("nickname"));
                    wechatMemberStores.setSex(jsonObject.getInt("sex"));
                    wechatMemberStores.setCity(jsonObject.getString("city"));
                    wechatMemberStores.setProvince(jsonObject.getString("province"));
                    wechatMemberStores.setCountry(jsonObject.getString("country"));
                    wechatMemberStores.setHeadImgUrl(jsonObject.getString("headimgurl"));
                    wechatMemberStores.setSubscribeTime(WechatMessageUtil.strToDateLong(WechatMessageUtil.formatTime(jsonObject.getString("subscribe_time"))));
                    wechatMemberStores.setRemark(jsonObject.getString("remark"));
                    wechatMemberStores.setGroupId(jsonObject.getInt("groupid"));
                    wechatMemberStores.setSubscribeScene(jsonObject.getString("subscribe_scene"));
                    wechatMemberStores.setQrScene(jsonObject.getInt("qr_scene"));
                    wechatMemberStores.setQrSceneStr(jsonObject.getString("qr_scene_str"));
                    wechatMemberStores.setLanguage(jsonObject.getString("language"));
                    wechatMemberStoresService.persist(wechatMemberStores);
                } else {
                    wechatMemberStores.setSubscribe(1);
                    wechatMemberStoresService.update(wechatMemberStores);
                }
            } else if (WechatMessageUtil.MESSAGE_EVENT_UNSUBSCRIBE.equals(event)) {
                // 取消关注公众号事件推送

                WechatMemberStores wechatMemberStores = wechatMemberStoresService.getByProerties("openid", fromUserName);
                if (wechatMemberStores != null) {
                    wechatMemberStores.setSubscribe(0);
                    wechatMemberStoresService.update(wechatMemberStores);
                }
            }
            System.out.println(event);
        }

        return responseMessage;
    }
}
