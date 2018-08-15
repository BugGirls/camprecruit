package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.WechatCard;
import com.jeefw.model.sys.WechatQrcodePuttingCard;
import com.jeefw.service.sys.WechatCardService;
import com.jeefw.service.sys.WechatQrcodePuttingCardService;
import core.enums.CardColorEnum;
import core.enums.CardPutStatusEnum;
import core.enums.CardShelfStatusEnum;
import core.enums.CardTypeEnum;
import core.util.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员活动
 *
 * @author Hystar
 * @date 2018/8/14
 */
@Controller
@RequestMapping(value = "/member/activity")
public class MemberActivityController extends JavaEEFrameworkBaseController implements Constant {

    @Resource
    private WechatCardService wechatCardService;

    @Resource
    private WechatQrcodePuttingCardService wechatQrcodePuttingCardService;

    /**
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
    @RequestMapping(value = "/get_card_detail", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getCardDetail(@RequestParam(value = "cardId") String cardId) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        if (StringUtils.isEmpty(cardId)) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        WechatCard wechatCard = wechatCardService.getByProerties("cardId", cardId);
        if (wechatCard == null) {
            resultMap.put("msg", "该卡券信息不存在");
            return resultMap;
        }

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
}
