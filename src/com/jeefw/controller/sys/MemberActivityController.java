package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.WechatCard;
import com.jeefw.model.sys.WechatMemberStores;
<<<<<<< HEAD
=======
import com.jeefw.model.sys.WechatQrcodePuttingCard;
>>>>>>> merge project
import com.jeefw.service.sys.WechatCardService;
import com.jeefw.service.sys.WechatMemberStoresService;
import com.jeefw.service.sys.WechatQrcodePuttingCardService;
import core.enums.CardColorEnum;
<<<<<<< HEAD
import core.enums.CardShelfStatusEnum;
import core.enums.CardTypeEnum;
import core.util.*;
=======
import core.enums.CardPutStatusEnum;
import core.enums.CardShelfStatusEnum;
import core.enums.CardTypeEnum;
import core.util.WechatUtil;
>>>>>>> merge project
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
=======
>>>>>>> merge project
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员活动
 *
<<<<<<< HEAD
 * 扫描识别（扫码识货）
 * scan discern
 *
=======
>>>>>>> merge project
 * @author Hystar
 * @date 2018/8/14
 */
@Controller
@RequestMapping(value = "/member/activity")
public class MemberActivityController extends JavaEEFrameworkBaseController implements Constant {

    @Resource
    private WechatCardService wechatCardService;

    @Resource
<<<<<<< HEAD
    private WechatMemberStoresService wechatMemberStoresService;

    /**
=======
    private WechatQrcodePuttingCardService wechatQrcodePuttingCardService;

    @Resource
    private WechatMemberStoresService wechatMemberStoresService;

    /**
     * 判断用户是否为会员
     *
     * @param openid
     */
    @RequestMapping(value = "judge_user_member_state", method = RequestMethod.GET)
    public String judgeUserMemberState(@RequestParam(value = "openid") String openid) {
        System.out.println(openid);
        WechatMemberStores wechatMemberStores = wechatMemberStoresService.getByProerties("openid", openid);
        if (wechatMemberStores == null) {
            // 如果不是会员，即没有关注公众号，需要先关注微信公众号
            return "redirect:https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzUxMzM5NDMwMg==&scene=126#wechat_redirect";
        } else {
            // 是会员，关注了微信公众号，则跳转到指定路径
            return "redirect:/member/activity/get_card_detail";
        }
    }

    /**
>>>>>>> merge project
     * 获取当前加盟商的所有卡券信息
     *
     * @return
     */
    @RequestMapping(value = "/get_card_list", method = RequestMethod.POST)
    @ResponseBody
    public List<WechatCard> getCardList() {
        List<WechatCard> wechatCardList = new ArrayList<>();
        Integer allianceId = getCurrentAllianceId();

        Map<String, String> sortMap = new HashMap<>();
        sortMap.put("id", "desc");
        String[] propName = {"allianceId", "cardShelfStatus"};
        Object[] propValue = {allianceId, CardShelfStatusEnum.PUTAWAY.getKey()};
        List<WechatCard> wechatCardListTemp = wechatCardService.queryByProerties(propName, propValue, sortMap);
        if (wechatCardListTemp != null && wechatCardListTemp.size() > 0) {
            for (WechatCard wechatCard : wechatCardListTemp) {
                if (wechatCard.getCardType().equals(CardTypeEnum.MEMBER_CARD.getKey())) {
                    continue;
                }
                wechatCard.setColor(CardColorEnum.valueOf(wechatCard.getColor()).getValue());
                wechatCardList.add(wechatCard);
            }
        }

        return wechatCardList;
    }

    /**
     * 获取卡券详情信息和投放信息
     *
     * @param cardId
     * @return
     */
<<<<<<< HEAD
    @RequestMapping(value = "/get_card_detail", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCardDetail(@RequestParam(value = "cardId") String cardId,
                                             @RequestParam(value = "openid", required = false) String openid) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
=======
    @RequestMapping(value = "/get_card_detail", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getCardDetail(@RequestParam(value = "cardId") String cardId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();
>>>>>>> merge project

        if (StringUtils.isEmpty(cardId)) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

<<<<<<< HEAD
        if (StringUtils.isEmpty(openid)) {
            // 微信网页授权URL
            String returnUrl = "/member/activity/judge_user_member_state?cardId=" + cardId;
            String url = PropertiesUtil.getProperty("localUrl") + "/wechat/auth?returnUrl=" + URLEncoder.encode(returnUrl, "UTF-8");
            // 文件生成路径
            String fileName = KeyUtil.generatorNonceStr() + ".png";
            String targetAddress = ImagePathUtil.getShopImagePath("qrcode");
            ImageUtil.makeDirPath(targetAddress);
            String relativeAddress = targetAddress + fileName;
            File dest = new File(ImagePathUtil.getImgBasePath() + relativeAddress);
            // 生成二维码
            QRCodeFactory qrCodeFactory = new QRCodeFactory();
            qrCodeFactory.CreatQrImage(url, "jpg", dest.getPath(), ImagePathUtil.getImgBasePath() + "\\fe\\assets\\img\\logo.png", new int[]{430, 430});
            resultMap.put("qrcode", relativeAddress);
        }

=======
>>>>>>> merge project
        WechatCard wechatCard = wechatCardService.getByProerties("cardId", cardId);
        if (wechatCard == null) {
            resultMap.put("msg", "该卡券信息不存在");
            return resultMap;
        }

<<<<<<< HEAD
        resultMap.put("success", true);
        resultMap.put("card", wechatCard);
        return resultMap;
    }

    /**
     * 判断用户是否为会员
     *
     * @param openid
     */
    @RequestMapping(value = "judge_user_member_state", method = RequestMethod.GET)
    public String judgeUserMemberState(@RequestParam(value = "openid") String openid,
                                       @RequestParam(value = "cardId") String cardId,
                                       HttpServletRequest request) throws Exception {
        System.out.println(openid);
        WechatMemberStores wechatMemberStores = wechatMemberStoresService.getByProerties("openid", openid);
        if (wechatMemberStores == null || wechatMemberStores.getSubscribe() == 0) {
            // 如果不是会员，即没有关注公众号，需要先关注微信公众号
            return "redirect:https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzU4MDYyNTU4MA==&scene=126#wechat_redirect";
        } else {
            // 是会员，关注了微信公众号，则跳转到指定路径
            request.setAttribute("cardId", cardId);
            return "getCard";
        }
    }

    /**
     * @param cardId
     * @return
     */
    @RequestMapping(value = "/js_put_card", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> JSPutCard(String cardId) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        String nonceStr = KeyUtil.generatorNonceStr();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        // 这个签名用于 调用config接口的时候传入的签名
        String signature1 = WechatUtil.getJSAPISign(nonceStr, timestamp);
        // 这个签名用于 卡券 api_ticket 接口的时候传入的签名
        String signature2 = WechatUtil.getCardSign(timestamp, nonceStr, cardId);

        // 返回配置信息
        paramMap.put("cardId", cardId);
        paramMap.put("timestamp", timestamp);
        paramMap.put("nonceStr", nonceStr);
        paramMap.put("appId", Const.APP_ID);
        paramMap.put("signature1", signature1);
        paramMap.put("signature2", signature2);

        return paramMap;
    }
=======
        WechatQrcodePuttingCard wechatQrcodePuttingCard = wechatQrcodePuttingCardService.getByProerties("wechatCardId", cardId);
        if (wechatQrcodePuttingCard == null) {
            // 如果该卡券在创建之后没有被投放过，则无法返回页面投放信息，因此需要自动投放卡券
            wechatQrcodePuttingCard = new WechatQrcodePuttingCard();
            wechatQrcodePuttingCard.setActionName("QR_CARD");
            wechatQrcodePuttingCard.setWechatCardId(cardId);
            Map<String, String> map = WechatUtil.createQrCodePutCard(wechatQrcodePuttingCard);
            if (map.get("errcode").equals("0") && map.get("errmsg").equals("ok")) {
                wechatQrcodePuttingCard.setAllianceId(allianceId);
                wechatQrcodePuttingCard.setTicket(map.get("ticket"));
                wechatQrcodePuttingCard.setUrl(map.get("url"));
                wechatQrcodePuttingCard.setShowQrcodeUrl(map.get("show_qrcode_url"));
                wechatQrcodePuttingCard.setCardId(wechatCard.getId());
                wechatQrcodePuttingCardService.persist(wechatQrcodePuttingCard);

                wechatCard.setCardPutStatus(CardPutStatusEnum.ALREADY_PUTTING.getKey());
                wechatCardService.update(wechatCard);
            } else {
                resultMap.put("msg", map.get("errmsg"));
                return resultMap;
            }
        }

        resultMap.put("success", true);
        resultMap.put("card", wechatCard);
        resultMap.put("qrcode", wechatQrcodePuttingCard.getShowQrcodeUrl());
        return resultMap;
    }
>>>>>>> merge project
}
