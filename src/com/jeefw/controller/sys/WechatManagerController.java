package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.*;
import com.jeefw.model.sys.vo.ResultPageVo;
import com.jeefw.service.sys.*;
import com.jeefw.service.sys.impl.WechatService;
import core.enums.*;
import core.support.QueryResult;
import core.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 微信对接
 *
 * @author Empress
 * @data 2018/7/8
 */
@Controller
@RequestMapping(value = "/wechat/manager")
public class WechatManagerController extends JavaEEFrameworkBaseController<WechatMaterialImage> implements Constant {

    private static final Logger logger = LoggerFactory.getLogger(WechatManagerController.class);

    @Resource
    private WechatMaterialImageService wechatMaterialImageService;

    @Resource
    private WechatCardService wechatCardService;

    @Resource
    private WechatQrcodePuttingCardService wechatQrcodePuttingCardService;

    @Resource
    private WechatService wechatService;

    @Resource
    private WechatPoiService wechatPoiService;

    @Resource
    private WechatUserConsumeCardService wechatUserConsumeCardService;

    /**
     * 验证消息是否来自微信服务器，需要在微信后台配置
     * 如果验证成功，返回参数 echostr
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "{appID}/check_signature", method = RequestMethod.GET)
    @ResponseBody
    public String checkSignature(@PathVariable(value = "appID") String appID,
                                 @RequestParam(value = "signature", required = false) String signature,
                                 @RequestParam(value = "timestamp") String timestamp,
                                 @RequestParam(value = "nonce") String nonce,
                                 @RequestParam(value = "echostr") String echostr) throws IOException {
        if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 接收来自微信发来的消息
     *
     * @param request
     */
    @RequestMapping(value = "{appID}/check_signature", method = RequestMethod.POST)
    @ResponseBody
    public String wechatServicePost(@PathVariable(value = "appID") String appID,
                                    HttpServletRequest request) throws Exception {
        String responseMessage = wechatService.processRequest(request);
        return responseMessage;
    }

    /**
     * 获取微信素材图片，只获取本地数据库中的素材图片
     *
     * @return
     */
    @RequestMapping(value = "/get_wechat_material_image_list_page", method = RequestMethod.POST)
    @ResponseBody
    public ResultPageVo getWechatMaterialImageListPage(HttpServletRequest request) {
        WechatMaterialImage wechatMaterialImage = new WechatMaterialImage();
        Integer allianceId = getCurrentAllianceId();

        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        wechatMaterialImage.set$eq_allianceId(allianceId);
        wechatMaterialImage.set$eq_useScene(MaterialUseSceneEnum.COMMON_USE.getCode());
        wechatMaterialImage.setFirstResult((pageNum - 1) * pageSize);
        wechatMaterialImage.setMaxResults(pageSize);
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put("id", "desc");
        wechatMaterialImage.setSortedConditions(sortedCondition);
        QueryResult<WechatMaterialImage> queryResult = wechatMaterialImageService.doPaginationQuery(wechatMaterialImage);
        ResultPageVo resultPageVo = new ResultPageVo();
        resultPageVo.setData(queryResult.getResultList());
        resultPageVo.setPageNum(pageNum);
        resultPageVo.setPageSize(pageSize);
        resultPageVo.setTotalCount(queryResult.getTotalCount());
        resultPageVo.setTotalPage((queryResult.getTotalCount() + pageSize - 1) / pageSize);

        return resultPageVo;
    }

    /**
     * 将图片上传到服务器
     *
     * @param base64Data base64格式的图片
     * @throws Exception
     */
    @RequestMapping(value = "/upload_material_image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadMaterialImage(@RequestParam(value = "base64Data") String base64Data) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        WechatMaterialImage materialImage = new WechatMaterialImage();

        // 判断图片大小是否超过5M
        Integer imageSize = ImageUtil.imageSize(base64Data);
        if (imageSize > 1024 * 1024 * 5) {
            resultMap.put("msg", "图片大小超过5M，当前图片大小：" + imageSize);
            return resultMap;
        }

        // 1、写入本地文件
        String relativeAddress;
        try {
            // 获取图片目录的相对路径
            String dest = ImagePathUtil.getShopImagePath("material", DateHelper.getCurrentDate());
            // 处理图片，并返回新图片的相对路径
            relativeAddress = ImageUtil.generateImage(materialImage, base64Data, dest);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }

        // 2、上传图片到微信服务器
        try {
            Map<String, String> map = WechatUtil.addEverMaterialExcludeVideo(ImagePathUtil.getImgBasePath() + relativeAddress, "image");
            if (!map.isEmpty() && StringUtils.isNotBlank(map.get("url")) && StringUtils.isNotBlank(map.get("media_id"))) {
                materialImage.setMediaId(map.get("media_id"));
                materialImage.setWechatImgUrl(map.get("url"));
            } else if (!map.isEmpty() && StringUtils.isNotBlank(map.get("errcode")) && StringUtils.isNotBlank(map.get("errmsg"))) {
                resultMap.put("msg", "上传到微信服务器失败，" + map.get("errmsg"));
                return resultMap;
            } else {
                resultMap.put("msg", "对接微信服务器出现未知错误");
                return resultMap;
            }
        } catch (Exception e) {
            FileUtil.deleteFile(ImagePathUtil.getImgBasePath() + relativeAddress);
            resultMap.put("msg", "上传到微信服务器失败");
            return resultMap;
        }

        // 3、持久化到本地数据库
        try {
            Integer allianceId = getCurrentAllianceId();
            materialImage.setAllianceId(allianceId);
            materialImage.setUseScene(MaterialUseSceneEnum.COMMON_USE.getCode());
            wechatMaterialImageService.persist(materialImage);
        } catch (Exception e) {
            logger.error(e.toString());
            FileUtil.deleteFile(ImagePathUtil.getImgBasePath() + relativeAddress);
            resultMap.put("msg", "持久化到本地数据库失败");
            return resultMap;
        }

        resultMap.put("result", "文件上传成功");
        resultMap.put("success", true);

        return resultMap;
    }

    /**
     * 删除素材图片：本地服务器图片和微信服务器图片
     *
     * @param materialImageId
     */
    @RequestMapping(value = "/delete_material_image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteMaterialImage(@RequestParam(value = "materialImageId") Integer materialImageId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (materialImageId == null) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        try {
            WechatMaterialImage materialImage = wechatMaterialImageService.get(materialImageId);
            if (materialImage != null) {
                // 删除微信服务器文件
                Map<String, String> map = WechatUtil.deleteEverMaterial(materialImage.getMediaId());
                if (!map.isEmpty() && map.get("errcode").equals("0") && map.get("errmsg").equals("ok")) {

                } else {
                    resultMap.put("msg", "删除微信服务器文件失败：" + map.get("errmsg"));
                    return resultMap;
                }

                // 删除本地服务器文件
                Boolean isOk = FileUtil.deleteFile(ImagePathUtil.getImgBasePath() + materialImage.getLocalImgUrl());
                if (!isOk) {
                    resultMap.put("msg", "删除本地服务器文件失败");
                    return resultMap;
                }

                // 删除数据库数据
                wechatMaterialImageService.delete(materialImage);

                resultMap.put("success", true);
                resultMap.put("msg", "删除成功");
                return resultMap;
            } else {
                resultMap.put("msg", "素材ID无效");
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 初始化卡券管理页面
     *
     * @return
     */
    @RequestMapping(value = "init_card_manager_page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> initCardManagerPage() {

        // 卡券类型
        Map<String, String> cardTypeMap = new HashMap<>();
        for (CardTypeEnum type : CardTypeEnum.values()) {
            if (type.getKey().equals("MEMBER_CARD")) {
                continue;
            }
            cardTypeMap.put(type.getKey(), type.getValue());
        }

        return cardTypeMap;
    }

    /**
     * 获取卡券分页信息
     *
     * @return
     */
    @RequestMapping(value = "get_wechat_card_list_page", method = RequestMethod.POST)
    @ResponseBody
    public ResultPageVo getWechatCardListPage(HttpServletRequest request) {
        WechatCard wechatCard = new WechatCard();
        Integer allianceId = getCurrentAllianceId();

        Integer firstResult = Integer.valueOf(request.getParameter("pageNum"));
        Integer maxResult = Integer.valueOf(request.getParameter("pageSize"));
        String cardType = request.getParameter("cardType");
        if (StringUtils.isNotBlank(cardType)) {
            wechatCard.set$eq_cardType(cardType);
        }
        wechatCard.set$eq_allianceId(allianceId);
        wechatCard.setFirstResult((firstResult - 1) * maxResult);
        wechatCard.setMaxResults(maxResult);
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put("id", "desc");
        wechatCard.setSortedConditions(sortedCondition);
        QueryResult<WechatCard> queryResult = wechatCardService.doPaginationQuery(wechatCard);

        ResultPageVo resultPageVo = new ResultPageVo();
        resultPageVo.setData(queryResult.getResultList());
        resultPageVo.setPageNum(firstResult);
        resultPageVo.setPageSize(maxResult);
        resultPageVo.setTotalCount(queryResult.getTotalCount());
        resultPageVo.setTotalPage((queryResult.getTotalCount() + maxResult - 1) / maxResult);

        return resultPageVo;
    }

    /**
     * 初始化创建卡券页面
     */
    @RequestMapping(value = "/init_create_card_page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initCreateCardPage() {
        Map<String, Object> resultMap = new HashMap<>();

        // 素材图片列表
        Integer allianceId = getCurrentAllianceId();
        String[] key = {"allianceId", "useScene"};
        Object[] value = {allianceId, MaterialUseSceneEnum.COMMON_USE.getCode()};
        List<WechatMaterialImage> wechatMaterialImageList = wechatMaterialImageService.queryByProerties(key, value);
        resultMap.put("wechatMaterialImageList", wechatMaterialImageList);

        // 卡券类型
        Map<String, String> cardTypeMap = new HashMap<>();
        for (CardTypeEnum type : CardTypeEnum.values()) {
            if (type.getKey().equals("MEMBER_CARD")) {
                continue;
            }
            cardTypeMap.put(type.getKey(), type.getValue());
        }
        resultMap.put("cardTypeMap", cardTypeMap);

        // 卡券码型
        Map<String, String> codeTypeMap = new HashMap<>();
        for (CodeTypeEnum codeTypeEnum : CodeTypeEnum.values()) {
            codeTypeMap.put(codeTypeEnum.getKey(), codeTypeEnum.getValue());
        }
        resultMap.put("codeTypeMap", codeTypeMap);

        // 卡券颜色
        Map<String, String> cardColorMap = new HashMap<>();
        for (CardColorEnum cardColorEnum : CardColorEnum.values()) {
            cardColorMap.put(cardColorEnum.getKey(), cardColorEnum.getValue());
        }
        resultMap.put("cardColorMap", cardColorMap);

        // 有效期使用的时间类型
        Map<String, String> dateInfoTypeMap = new HashMap<>();
        for (DateInfoType dateInfoType : DateInfoType.values()) {
            if (dateInfoType.getKey().equals("DATE_TYPE_PERMANENT")) {
                continue;
            }
            dateInfoTypeMap.put(dateInfoType.getKey(), dateInfoType.getValue());
        }
        resultMap.put("dateInfoTypeMap", dateInfoTypeMap);

        // 卡券使用时段类型
        Map<String, String> advancedInfoTypeMap = new HashMap<>();
        for (AdvancedInfoType advancedInfoType : AdvancedInfoType.values()) {
            advancedInfoTypeMap.put(advancedInfoType.getKey(), advancedInfoType.getValue());
        }
        resultMap.put("advancedInfoTypeMap", advancedInfoTypeMap);

        // 商家服务类型
        Map<String, String> businessServiceMap = new HashMap<>();
        for (BusinessServiceEnum businessServiceEnum : BusinessServiceEnum.values()) {
            businessServiceMap.put(businessServiceEnum.getKey(), businessServiceEnum.getValue());
        }
        resultMap.put("businessServiceMap", businessServiceMap);

        return resultMap;
    }

    /**
     * 创建卡券
     *
     * @param wechatCard
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createCard(@RequestBody WechatCard wechatCard) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        try {
            // 1、推送到微信服务器
            wechatCard.setBindOpenid(0);
            wechatCard.setUseCustomCode(0);
            wechatCard.setCanGiveFriend(1);
            wechatCard.setCanShare(1);
            JSONObject jsonObject = WechatUtil.createCard(wechatCard);
            System.out.println(jsonObject);
            if (jsonObject.get("errcode").equals(0) && jsonObject.get("errmsg").equals("ok")) {
                String cardId = jsonObject.get("card_id").toString();

                // 2、保存到本地数据库
                wechatCard.setCardId(cardId);
                wechatCard.setAllianceId(allianceId);
                wechatCard.setCardPutStatus(CardPutStatusEnum.WAIT_PUTTING.getKey());
                wechatCard.setCardShelfStatus(CardShelfStatusEnum.PUTAWAY.getKey());
                wechatCardService.persist(wechatCard);
            } else {
                resultMap.put("msg", jsonObject.get("errmsg"));
                return resultMap;
            }

            resultMap.put("success", true);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 扫描二维码投放卡券
     *
     * @param wechatQrcodePuttingCard
     * @throws Exception
     */
    @RequestMapping(value = "/qrcode_putting_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> qrcodePuttingCard(@RequestBody WechatQrcodePuttingCard wechatQrcodePuttingCard) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        try {
            Map<String, String> map = WechatUtil.createQrCodePutCard(wechatQrcodePuttingCard);
            logger.info(GSON.toJson(map));
            if (map.get("errcode").equals("0") && map.get("errmsg").equals("ok")) {
                // 从数据库中获取该卡券的投放信息，如果已经投放过，则更新二维码信息，否则创建新的投放信息
                WechatQrcodePuttingCard qrcodePuttingCard = wechatQrcodePuttingCardService.getByProerties("wechatCardId", wechatQrcodePuttingCard.getWechatCardId());
                if (qrcodePuttingCard != null) {
                    qrcodePuttingCard.setShowQrcodeUrl(map.get("show_qrcode_url"));
                    wechatQrcodePuttingCardService.update(qrcodePuttingCard);
                } else {
                    wechatQrcodePuttingCard.setAllianceId(allianceId);
                    wechatQrcodePuttingCard.setTicket(map.get("ticket"));
                    wechatQrcodePuttingCard.setUrl(map.get("url"));
                    wechatQrcodePuttingCard.setShowQrcodeUrl(map.get("show_qrcode_url"));
                    wechatQrcodePuttingCardService.persist(wechatQrcodePuttingCard);

                    WechatCard wechatCard = wechatCardService.getByProerties("id", wechatQrcodePuttingCard.getCardId());
                    wechatCard.setCardPutStatus(CardPutStatusEnum.ALREADY_PUTTING.getKey());
                    wechatCard.setCardShelfStatus(CardShelfStatusEnum.PUTAWAY.getKey());
                    wechatCardService.update(wechatCard);
                }

                resultMap.put("success", true);
                resultMap.put("wechatQrcodePuttingCard", wechatQrcodePuttingCard);
            } else {
                resultMap.put("msg", map.get("errmsg"));
            }
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 下载二维码
     *
     * @param url
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/download_qrcode")
    public void downloadQrcode(@RequestParam(value = "url") String url, HttpServletResponse response) throws IOException {
        DownloadUrl.download(url, response);
    }

    /**
     * 线下核销
     *
     * @param code
     * @throws Exception
     */
    @RequestMapping(value = "/offline_consume_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> offlineConsumeCard(@RequestParam(value = "code") String code) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (StringUtils.isBlank(code)) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        // 查询code
        JSONObject jsonObject = WechatUtil.getCardForCode(code);
        if ((jsonObject.get("errcode").equals(0)) && ("ok").equals(jsonObject.get("errmsg"))) {
            boolean canConsume = (boolean) jsonObject.get("can_consume");

            if (canConsume) {
                // 核销卡券
                JSONObject comsumeObject = WechatUtil.consumeCard(code);
                if ((comsumeObject.get("errcode").equals(0)) && ("ok").equals(comsumeObject.get("errmsg"))) {
                    String consumeOpenid = comsumeObject.get("openid").toString();
                    Map<String, String> cardMap = (Map<String, String>) comsumeObject.get("card");
                    String cardId = cardMap.get("card_id");

                    WechatUserConsumeCard wechatUserConsumeCard = new WechatUserConsumeCard();
                    wechatUserConsumeCard.setId(KeyUtil.generatorUniqueKey());
                    wechatUserConsumeCard.setOpenid(consumeOpenid);
                    wechatUserConsumeCard.setCardId(cardId);
                    wechatUserConsumeCard.setUserCardCode(code);
                    wechatUserConsumeCard.setConsumeSource("FROM_API");
                    wechatUserConsumeCard.setCreateTime(new Date());
                    wechatUserConsumeCardService.persist(wechatUserConsumeCard);

                    resultMap.put("success", true);
                    resultMap.put("msg", "卡券核销成功");
                    return resultMap;
                } else {
                    resultMap.put("msg", "核销卡券出现异常：" + jsonObject.get("errmsg"));
                    return resultMap;
                }
            } else {
                UserCardStatusEnum userCardStatusEnum = UserCardStatusEnum.keyOf((String) jsonObject.get("user_card_status"));
                if (userCardStatusEnum == null) {
                    throw new Exception("没有找到对应的枚举");
                } else {
                    resultMap.put("msg", "卡券状态异常，该卡券已" + userCardStatusEnum.getValue());
                    return resultMap;
                }
            }
        } else {
            resultMap.put("msg", "查询卡券信息出现异常：" + jsonObject.get("errmsg"));
            return resultMap;
        }
    }

    /**
     * 删除微信服务器上的卡券信息
     *
     * @param cardId
     * @return
     */
    @RequestMapping(value = "/delete_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCard(@RequestParam(value = "cardId") String cardId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        try {
            if (StringUtils.isNotBlank(cardId)) {
                WechatCard wechatCard = wechatCardService.getByProerties("cardId", cardId);
                // 删除本地服务器的卡券信息
                wechatCardService.delete(wechatCard);

                // 删除微信服务器的卡券信息
                Map<String, String> map = WechatUtil.deleteCard(cardId);
                if (map.get("errcode").equals("0") && map.get("errmsg").equals("ok")) {
                    resultMap.put("success", true);
                } else {
                    resultMap.put("msg", map.get("errmsg"));
                }
            } else {
                resultMap.put("msg", "卡券ID为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 上架/下架卡券
     *
     * @param cardId
     * @param cardShelfStatus
     * @return
     */
    @RequestMapping(value = "/shelf_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shelfCard(@RequestParam(value = "cardId") String cardId,
                                         @RequestParam(value = "cardShelfStatus") Integer cardShelfStatus) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        try {
            if (StringUtils.isNotBlank(cardId)) {
                WechatCard wechatCard = wechatCardService.getByProerties("cardId", cardId);
                if (wechatCard != null) {
                    if (CardShelfStatusEnum.keyOf(cardShelfStatus) != null) {
                        wechatCard.setCardShelfStatus(cardShelfStatus);
                        wechatCardService.update(wechatCard);

                        resultMap.put("success", true);
                    } else {
                        resultMap.put("msg", "卡券状态错误");
                    }
                } else {
                    resultMap.put("msg", "该卡券不存在");
                }
            } else {
                resultMap.put("msg", "卡券ID为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 获取门店分页信息
     *
     * @return
     */
    @RequestMapping(value = "get_poi_card_list_page", method = RequestMethod.POST)
    @ResponseBody
    public ResultPageVo getPoiCardListPage(HttpServletRequest request) {
        WechatPoi wechatPoi = new WechatPoi();
        Integer allianceId = getCurrentAllianceId();

        Integer firstResult = Integer.valueOf(request.getParameter("pageNum"));
        Integer maxResult = Integer.valueOf(request.getParameter("pageSize"));
        String branchName = request.getParameter("searchKey");

        if (StringUtils.isNotBlank(branchName)) {
            wechatPoi.set$like_branchName(branchName);
        }
        wechatPoi.set$eq_allianceId(allianceId);
        wechatPoi.setFirstResult((firstResult - 1) * maxResult);
        wechatPoi.setMaxResults(maxResult);
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put("sid", "desc");
        wechatPoi.setSortedConditions(sortedCondition);
        QueryResult<WechatPoi> queryResult = wechatPoiService.doPaginationQuery(wechatPoi);

        ResultPageVo resultPageVo = new ResultPageVo();
        resultPageVo.setData(queryResult.getResultList());
        resultPageVo.setPageNum(firstResult);
        resultPageVo.setPageSize(maxResult);
        resultPageVo.setTotalCount(queryResult.getTotalCount());
        resultPageVo.setTotalPage((queryResult.getTotalCount() + maxResult - 1) / maxResult);

        return resultPageVo;
    }

    /**
     * 初始化创建门店信息页面
     *
     * @return
     */
    @RequestMapping(value = "init_create_poi_page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initCreatePoiPage() {
        Map<String, Object> resultMap = new HashMap<>();

        // 素材图片列表
        Integer allianceId = getCurrentAllianceId();
        List<WechatMaterialImage> wechatMaterialImageList = wechatMaterialImageService.queryByProerties("allianceId", allianceId);
        resultMap.put("wechatMaterialImageList", wechatMaterialImageList);

        // 坐标类型
        Map<Integer, String> offsetTypeMap = new HashMap<>();
        for (OffsetTypeEnum offsetTypeEnum : OffsetTypeEnum.values()) {
            offsetTypeMap.put(offsetTypeEnum.getCode(), offsetTypeEnum.getValue());
        }
        resultMap.put("offsetTypeMap", offsetTypeMap);

        return resultMap;
    }

    /**
     * 创建门店
     *
     * @param wechatPoi
     * @return
     */
    @RequestMapping(value = "create_poi", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createPoi(@RequestBody WechatPoi wechatPoi) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        try {
            // 1、推送到微信服务器
            Map<String, String> map = WechatUtil.createPoi(wechatPoi);
            if (("0").equals(map.get("errcode")) && ("ok").equals(map.get("errmsg"))) {

                // 2、保存到本地服务器
                wechatPoi.setSid(KeyUtil.generatorUniqueKey());
                wechatPoi.setPoiId(map.get("poi_id"));
                wechatPoi.setAllianceId(allianceId);
                wechatPoi.setCreateTime(new Date());
                wechatPoiService.persist(wechatPoi);
            } else {
                resultMap.put("msg", map.get("errmsg"));
                return resultMap;
            }

            resultMap.put("success", true);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 上传背景图片素材
     *
     * @param request
     */
    @RequestMapping(value = "/upload_background_image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadBackgroundImage(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String uploadPath = null;
        WechatMaterialImage wechatMaterialImage = new WechatMaterialImage();
        Integer allianceId = getCurrentAllianceId();
        String wechatReturnUrl = null;

        // 获取使用场景
        Integer scene = Integer.valueOf(request.getParameter("scene"));

        // 1、写入到本地服务器
        try {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (request instanceof MultipartHttpServletRequest) {
                // 将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iterator = multiRequest.getFileNames();
                // 检查form中是否有enctype="multipart/form-data"
                if (multipartResolver.isMultipart(request) && iterator.hasNext()) {
                    // 获取multiRequest 中所有的文件名
                    while (iterator.hasNext()) {
                        // 适配名字重复的文件
                        List<MultipartFile> fileRows = multiRequest.getFiles(iterator.next().toString());
                        if (fileRows != null && fileRows.size() != 0) {
                            for (MultipartFile multipartFile : fileRows) {
                                if (multipartFile != null && !multipartFile.isEmpty()) {
                                    // 生成新的文件名
                                    String fileName = ImageUtil.generateNewFileName(multipartFile.getOriginalFilename());
                                    // 生成文件上传的相对路径（getShopImagePath()方法的第一个参数用于区分上传的素材为门店专用，第二个参数用于通过日期进行归类）
                                    uploadPath = ImagePathUtil.getShopImagePath(MaterialUseSceneEnum.codeOf(scene).getValue(), DateHelper.getCurrentDate()) + fileName;
                                    ImageUtil.makeDirPath(uploadPath);
                                    File file = new File(ImagePathUtil.getImgBasePath() + uploadPath);
                                    multipartFile.transferTo(file);

                                    wechatMaterialImage.setLocalImgUrl(uploadPath);
                                    wechatMaterialImage.setTitle(fileName);
                                    wechatMaterialImage.setCreateTime(new Date());
                                    wechatMaterialImage.setAllianceId(allianceId);
                                    wechatMaterialImage.setUseScene(MaterialUseSceneEnum.codeOf(scene).getCode());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            resultMap.put("success", false);
            resultMap.put("msg", ex.getMessage());
            return resultMap;
        }

        // 2、同步到微信服务器
        try {
            Map<String, String> map = WechatUtil.uploadImageForPoi(ImagePathUtil.getImgBasePath() + uploadPath);
            if (!map.isEmpty() && StringUtils.isNotBlank(map.get("url"))) {
                wechatMaterialImage.setWechatImgUrl(map.get("url"));
                wechatReturnUrl = map.get("url");
            } else {
                resultMap.put("success", false);
                resultMap.put("msg", "同步到微信服务器失败：" + map.get("errmsg"));
                return resultMap;
            }
        } catch (Exception e) {
            FileUtil.deleteFile(ImagePathUtil.getImgBasePath() + uploadPath);
            resultMap.put("success", false);
            resultMap.put("msg", "同步到微信服务器出现异常：" + e.getMessage());
            return resultMap;
        }

        // 3、持久化到本地数据库
        try {
            wechatMaterialImageService.persist(wechatMaterialImage);
        } catch (Exception e) {
            FileUtil.deleteFile(ImagePathUtil.getImgBasePath() + uploadPath);
            resultMap.put("success", false);
            resultMap.put("msg", "保存到本地数据库出现异常：" + e.getMessage());
            return resultMap;
        }

        resultMap.put("success", true);
        resultMap.put("url", wechatReturnUrl);
        return resultMap;
    }

    /**
     * 初始化修改门店信息页面
     *
     * @return
     */
    @RequestMapping(value = "init_update_poi_page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initUpdatePoiPage(@RequestParam(value = "sid") String sid) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (StringUtils.isBlank(sid)) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        WechatPoi wechatPoi = wechatPoiService.getByProerties("sid", sid);
        if (wechatPoi == null) {
            resultMap.put("msg", "门店信息不存在");
            return resultMap;
        }

        // 素材图片列表
        Integer allianceId = getCurrentAllianceId();
        List<WechatMaterialImage> wechatMaterialImageList = wechatMaterialImageService.queryByProerties("allianceId", allianceId);

        // 坐标类型
        Map<Integer, String> offsetTypeMap = new HashMap<>();
        for (OffsetTypeEnum offsetTypeEnum : OffsetTypeEnum.values()) {
            offsetTypeMap.put(offsetTypeEnum.getCode(), offsetTypeEnum.getValue());
        }

        resultMap.put("success", true);
        resultMap.put("wechatPoi", wechatPoi);
        resultMap.put("wechatMaterialImageList", wechatMaterialImageList);
        resultMap.put("offsetTypeMap", offsetTypeMap);

        return resultMap;
    }

    /**
     * 修改门店信息
     *
     * @param wechatPoi
     * @return
     */
    @RequestMapping(value = "update_poi", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePoi(@RequestBody WechatPoi wechatPoi) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        try {
            // 1、推送到微信服务器
            Map<String, String> map = WechatUtil.updatePoi(wechatPoi);
            if (("0").equals(map.get("errcode")) && ("ok").equals(map.get("errmsg"))) {

                // 2、修改状态 - 正在修改（微信审核）
                WechatPoi wechatPoiTemp = wechatPoiService.getByProerties("sid", wechatPoi.getSid());
                wechatPoiTemp.setUpdateStatus(PoiUpdateStatusEnum.UPDATING.getCode());
                wechatPoiService.update(wechatPoi);
            } else {
                resultMap.put("msg", map.get("errmsg"));
                return resultMap;
            }

            resultMap.put("success", true);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 删除门店
     *
     * @param sid
     * @return
     */
    @RequestMapping(value = "delete_poi", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deletePoi(@RequestParam(value = "sid") String sid) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (StringUtils.isBlank(sid)) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        WechatPoi wechatPoi = wechatPoiService.getByProerties("sid", sid);
        if (wechatPoi == null) {
            resultMap.put("msg", "门店不存在");
            return resultMap;
        }

        try {
            Map<String, String> map = WechatUtil.deletePoi(wechatPoi.getPoiId());
            if (("0").equals(map.get("errcode")) && ("ok").equals(map.get("errmsg"))) {
                wechatPoiService.delete(wechatPoi);
            } else {
                resultMap.put("msg", map.get("errmsg"));
                return resultMap;
            }
            resultMap.put("success", true);
            resultMap.put("msg", "门店删除成功");
            return resultMap;
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /**
     * 获取会员卡分页信息
     *
     * @return
     */
    @RequestMapping(value = "get_wechat_member_card_list_page", method = RequestMethod.POST)
    @ResponseBody
    public ResultPageVo getWechatMemberCardListPage(HttpServletRequest request) {
        WechatCard wechatMemberCard = new WechatCard();
        Integer allianceId = getCurrentAllianceId();

        Integer firstResult = Integer.valueOf(request.getParameter("pageNum"));
        Integer maxResult = Integer.valueOf(request.getParameter("pageSize"));
        String title = request.getParameter("title");
        if (StringUtils.isNotBlank(title)) {
            wechatMemberCard.set$like_title(title);
        }
        wechatMemberCard.set$eq_allianceId(allianceId);
        wechatMemberCard.set$eq_cardType(CardTypeEnum.MEMBER_CARD.getKey());
        wechatMemberCard.setFirstResult((firstResult - 1) * maxResult);
        wechatMemberCard.setMaxResults(maxResult);
        Map<String, String> sortedCondition = new HashMap<>();
        sortedCondition.put("id", "desc");
        wechatMemberCard.setSortedConditions(sortedCondition);
        QueryResult<WechatCard> queryResult = wechatCardService.doPaginationQuery(wechatMemberCard);

        ResultPageVo resultPageVo = new ResultPageVo();
        resultPageVo.setData(queryResult.getResultList());
        resultPageVo.setPageNum(firstResult);
        resultPageVo.setPageSize(maxResult);
        resultPageVo.setTotalCount(queryResult.getTotalCount());
        resultPageVo.setTotalPage((queryResult.getTotalCount() + maxResult - 1) / maxResult);

        return resultPageVo;
    }

    /**
     * 初始化创建会员卡页面
     */
    @RequestMapping(value = "/init_create_member_card_page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> initCreateMemberCardPage() {
        Map<String, Object> resultMap = new HashMap<>();

        // 素材图片列表
        Integer allianceId = getCurrentAllianceId();
        String[] key = {"allianceId", "useScene"};
        Object[] value = {allianceId, MaterialUseSceneEnum.COMMON_USE.getCode()};
        List<WechatMaterialImage> wechatMaterialImageList = wechatMaterialImageService.queryByProerties(key, value);
        resultMap.put("wechatMaterialImageList", wechatMaterialImageList);

        // 卡券码型
        Map<String, String> codeTypeMap = new HashMap<>();
        for (CodeTypeEnum codeTypeEnum : CodeTypeEnum.values()) {
            codeTypeMap.put(codeTypeEnum.getKey(), codeTypeEnum.getValue());
        }
        resultMap.put("codeTypeMap", codeTypeMap);

        // 卡券颜色
        Map<String, String> cardColorMap = new HashMap<>();
        for (CardColorEnum cardColorEnum : CardColorEnum.values()) {
            cardColorMap.put(cardColorEnum.getKey(), cardColorEnum.getValue());
        }
        resultMap.put("cardColorMap", cardColorMap);

        // 有效期使用的时间类型
        Map<String, String> dateInfoTypeMap = new HashMap<>();
        for (DateInfoType dateInfoType : DateInfoType.values()) {
            dateInfoTypeMap.put(dateInfoType.getKey(), dateInfoType.getValue());
        }
        resultMap.put("dateInfoTypeMap", dateInfoTypeMap);

        // 卡券使用时段类型
        Map<String, String> advancedInfoTypeMap = new HashMap<>();
        for (AdvancedInfoType advancedInfoType : AdvancedInfoType.values()) {
            advancedInfoTypeMap.put(advancedInfoType.getKey(), advancedInfoType.getValue());
        }
        resultMap.put("advancedInfoTypeMap", advancedInfoTypeMap);

        // 商家服务类型
        Map<String, String> businessServiceMap = new HashMap<>();
        for (BusinessServiceEnum businessServiceEnum : BusinessServiceEnum.values()) {
            businessServiceMap.put(businessServiceEnum.getKey(), businessServiceEnum.getValue());
        }
        resultMap.put("businessServiceMap", businessServiceMap);

        // 会员信息类目名称
        Map<String, String> memberInfoTypeMap = new HashMap<>();
        for (NameTypeEnum nameTypeEnum : NameTypeEnum.values()) {
            memberInfoTypeMap.put(nameTypeEnum.getKey(), nameTypeEnum.getValue());
        }
        resultMap.put("memberInfoTypeMap", memberInfoTypeMap);

        return resultMap;
    }

    /**
     * 创建微信会员卡
     *
     * @param wechatCard
     */
    @RequestMapping(value = "create_member_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createMemberCard(@RequestBody WechatCard wechatCard) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        if (wechatCard == null) {
            resultMap.put("msg", "参数错误");
            return resultMap;
        }

        try {
            // 上传到微信服务器
            wechatCard.setAutoActivate(1);
            wechatCard.setWxActivate(1);
            wechatCard.setSupplyBonus(1);
            wechatCard.setSupplyBalance(0);
            wechatCard.setSwipeCard(1);
            wechatCard.setPayAndQrcode(1);
            wechatCard.setBindOpenid(0);
            wechatCard.setUseCustomCode(0);
            wechatCard.setCanGiveFriend(1);
            wechatCard.setCanShare(1);
            wechatCard.setNeedPushOnView(1);
            JSONObject jsonObject = WechatUtil.createMemberCard(wechatCard);
            System.out.println(jsonObject);
            if ((jsonObject.get("errcode").equals(0)) && ("ok").equals(jsonObject.get("errmsg"))) {
                // 保存到本地数据库
                wechatCard.setAllianceId(allianceId);
                wechatCard.setCardId(jsonObject.get("card_id").toString());
                wechatCard.setCardPutStatus(CardPutStatusEnum.WAIT_PUTTING.getKey());
                wechatCard.setCardShelfStatus(CardShelfStatusEnum.PUTAWAY.getKey());
                wechatCard.setCardType(CardTypeEnum.MEMBER_CARD.getKey());
                wechatCardService.persist(wechatCard);

                resultMap.put("success", true);
                return resultMap;
            } else {
                resultMap.put("msg", jsonObject.get("errmsg"));
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * 扫描二维码投放会员卡
     *
     * @param wechatQrcodePuttingCard
     * @throws Exception
     */
    @RequestMapping(value = "/qrcode_putting_member_card", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> qrcodePuttingMemberCard(@RequestBody WechatQrcodePuttingCard wechatQrcodePuttingCard) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        try {
            Map<String, String> map = WechatUtil.createQrCodePutCard(wechatQrcodePuttingCard);
            logger.info(GSON.toJson(map));
            if (map.get("errcode").equals("0") && map.get("errmsg").equals("ok")) {
                wechatQrcodePuttingCard.setAllianceId(allianceId);
                wechatQrcodePuttingCard.setTicket(map.get("ticket"));
                wechatQrcodePuttingCard.setUrl(map.get("url"));
                wechatQrcodePuttingCard.setShowQrcodeUrl(map.get("show_qrcode_url"));
                wechatQrcodePuttingCardService.persist(wechatQrcodePuttingCard);

                WechatCard wechatCard = wechatCardService.getByProerties("id", wechatQrcodePuttingCard.getCardId());
                wechatCard.setCardPutStatus(CardPutStatusEnum.ALREADY_PUTTING.getKey());
                wechatCardService.merge(wechatCard);

                resultMap.put("success", true);
                resultMap.put("wechatQrcodePuttingCard", wechatQrcodePuttingCard);
            } else {
                resultMap.put("msg", map.get("errmsg"));
            }
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return resultMap;
    }

}
