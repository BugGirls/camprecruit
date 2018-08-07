package test;

import com.jeefw.model.sys.WechatCard;
import com.jeefw.model.sys.bo.CardMessage;
import core.enums.*;
import core.util.GSON;
import core.util.WechatUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * @author Hystar
 * @date 2018/3/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class WechatUtilTest {

    /**
     * 新增永久其他素材
     */
    @Test
    public void addEverMaterialExcludeVideoTest() throws Exception {
//        WechatUtil.getAccessToken();
        Map<String, String> resultMap = WechatUtil.addEverMaterialExcludeVideo("D:\\img8.png", "image");
        System.out.println(GSON.toJson(resultMap));
    }

    /**
     * 上传门店专用的图片
     */
    @Test
    public void uploadImageForPoiTest() throws Exception {
        WechatUtil.getAccessToken();
        Map<String, String> resultMap = WechatUtil.uploadImageForPoi("D:\\img8.png");
        System.out.println(GSON.toJson(resultMap));
    }

    @Test
    public void getCodeForCardTest() throws Exception {
        WechatUtil.getAccessToken();
        JSONObject jsonObject = WechatUtil.getCardForCode("905532575849");
        System.out.println(GSON.toJson(jsonObject));
    }

    @Test
    public void consumeCardTest() throws Exception {
        WechatUtil.getAccessToken();
        JSONObject jsonObject = WechatUtil.consumeCard("905532575849");
        System.out.println(GSON.toJson(jsonObject));
    }


    @Test
    public void deleteFile() {
        try {
            FileUtils.forceDeleteOnExit(new File("E:\\BugGirl_workspaces\\camprecruit\\out\\artifacts\\camprecruit_war_exploded\\upload/a4795f181c4346b5a1ba0c25bd14533e.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建卡券
     *
     * @throws Exception
     */
    @Test
    public void createCard() throws Exception{
        WechatUtil.getAccessToken();

        WechatCard wechatCard = new WechatCard();
        wechatCard.setLogoUrl("http://mmbiz.qpic.cn/mmbiz_png/gZ12Q0BUFkGuZhj7qjI81SyTJR8l9oQ0jbPibVC9Ll3LrkzqyaRmP6NHFqoCz2A5YLicBwdTowX2M5df3X6fkWNg/0?wx_fmt=png");
        wechatCard.setCodeType(CodeTypeEnum.QRCODE.getKey());
        wechatCard.setBrandName("海底捞");
        wechatCard.setTitle("情侣套餐1000元券");
        wechatCard.setColor(CardColorEnum.Color010.getKey());
        wechatCard.setNotice("使用时向服务员出示此券");
        wechatCard.setServicePhone("40012234");
        wechatCard.setDescription("不可与其他优惠同享");

        wechatCard.setDateType(DateInfoType.DATE_TYPE_PERMANENT.getKey());
        wechatCard.setBeginTimestamp(System.currentTimeMillis() / 1000);
        wechatCard.setEndTimestamp(DateUtils.addDays(new Date(), 10).getTime() / 1000);

        wechatCard.setQuantity(500);

        wechatCard.setCanShare(1);
        wechatCard.setCanGiveFriend(1);
        wechatCard.setCenterTitle("查看详情");
        wechatCard.setCenterSubTitle("查看优惠券详情");
        wechatCard.setCenterUrl("www.qq.com");
        wechatCard.setCustomUrlName("立即使用");
        wechatCard.setCustomUrl("http://www.qq.com");
        wechatCard.setCustomUrlSubTitle("使用方式");
        wechatCard.setPromotionUrlName("更多优惠");
        wechatCard.setPromotionUrl("http://www.baidu.com");

        wechatCard.setDealDetail("以下锅底2选1（有菌王锅、麻辣锅、大骨锅、番茄锅、清补 凉锅、酸菜鱼锅可选）：\\n大锅1份 12元\\n小锅2份 16元");
        wechatCard.setCardType("GROUPON");

        Map<String, String> resultMap = WechatUtil.createCard(wechatCard);
        System.out.println(GSON.toJson(resultMap));
    }

    @Test
    public void puttingCard() throws Exception {
//        WechatUtil.getAccessToken();
//
//        WechatQrcodePuttingCard wechatQrcodePuttingCard = new WechatQrcodePuttingCard();
//        wechatQrcodePuttingCard.setWechatCardId("pfiRq0c7KjuvY5Sdy-P1TyUHMK20");
//        wechatQrcodePuttingCard.setActionName("QR_CARD");
//
//        Map<String, String> resultMap = WechatUtil.createQrCodePutCard(wechatQrcodePuttingCard);
//        System.out.println(GSON.toJson(resultMap));
        String s = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        System.out.println(s);
    }

    /**
     * 创建会员卡
     *
     * @throws Exception
     */
    @Test
    public void createMemberCard() throws Exception{
        WechatUtil.getAccessToken();

        WechatCard wechatMemberCard = new WechatCard();
        wechatMemberCard.setBackgroundPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/gZ12Q0BUFkFEHyoxIQSYGicBGCpHtyRNqq6K7omxJvRKM1aoVf9oIspcsVlmuzsT4Ryo0kAxGyB1ASKKicO0DmYg/0?wx_fmt=jpeg");
        wechatMemberCard.setPrerogative("test_prerogative");
        wechatMemberCard.setAutoActivate(1);
        wechatMemberCard.setWxActivate(1);
        wechatMemberCard.setSupplyBonus(1);
        wechatMemberCard.setSupplyBalance(0);
        wechatMemberCard.setBonusRules("积分规则：1》");
        wechatMemberCard.setBonusCleared("积分清零规则：2》");
//        wechatMemberCard.setNameType("FIELD_NAME_TYPE_LEVEL");
//        wechatMemberCard.setUrlType("http://www.qq.com");
//        wechatMemberCard.setActivateUrl("http://www.qq.com");
//        wechatMemberCard.setCustomCellName("使用入口2");
//        wechatMemberCard.setCustomCellTips("激活后显示");
//        wechatMemberCard.setCustomCellUrl("http://www.qq.com");
        wechatMemberCard.setCostMoneyUnit(100);
        wechatMemberCard.setIncreaseBonus(1);
        wechatMemberCard.setMaxIncreaseBonus(200);
        wechatMemberCard.setInitIncreaseBonus(200);
        wechatMemberCard.setCostBonusUnit(5);
        wechatMemberCard.setReduceMoney(100);
        wechatMemberCard.setLeastMoneyToUseBonus(1000);
        wechatMemberCard.setMaxReduceBonus(50);
        wechatMemberCard.setDiscount(10);
        wechatMemberCard.setSwipeCard(1);
        wechatMemberCard.setPayAndQrcode(1);
        wechatMemberCard.setUseCustomCode(0);
        wechatMemberCard.setBindOpenid(0);
        wechatMemberCard.setLogoUrl("http://mmbiz.qpic.cn/mmbiz_png/gZ12Q0BUFkGuZhj7qjI81SyTJR8l9oQ0jbPibVC9Ll3LrkzqyaRmP6NHFqoCz2A5YLicBwdTowX2M5df3X6fkWNg/0?wx_fmt=png");
        wechatMemberCard.setCodeType(CodeTypeEnum.QRCODE.getKey());
        wechatMemberCard.setBrandName("马小北");
        wechatMemberCard.setTitle("马小北专享");
        wechatMemberCard.setColor(CardColorEnum.Color010.getKey());
        wechatMemberCard.setNotice("使用时向郝琼飞出示会员卡");
        wechatMemberCard.setServicePhone("15639076350");
        wechatMemberCard.setDescription("不可与其他优惠同享");

        wechatMemberCard.setDateType(DateInfoType.DATE_TYPE_PERMANENT.getKey());
        wechatMemberCard.setQuantity(500);

        wechatMemberCard.setCanShare(1);
        wechatMemberCard.setCanGiveFriend(1);
//        wechatMemberCard.setCenterTitle("查看详情");
//        wechatMemberCard.setCenterSubTitle("查看优惠券详情");
//        wechatMemberCard.setCenterUrl("www.qq.com");
//        wechatMemberCard.setCustomUrlName("立即使用");
//        wechatMemberCard.setCustomUrl("http://www.qq.com");
//        wechatMemberCard.setCustomUrlSubTitle("使用方式");
//        wechatMemberCard.setPromotionUrlName("更多优惠");
//        wechatMemberCard.setPromotionUrl("http://www.baidu.com");

        wechatMemberCard.setSwipeCard(1);
        wechatMemberCard.setPayAndQrcode(1);

        wechatMemberCard.setBusinessService(BusinessServiceEnum.BIZ_SERVICE_FREE_WIFI.getKey());

        wechatMemberCard.setAdvancedType(AdvancedInfoType.MONDAY.getKey());
        wechatMemberCard.setBeginHour(9);
        wechatMemberCard.setBeginMinute(10);
        wechatMemberCard.setEndHour(20);
        wechatMemberCard.setEndMinute(10);

        Map<String, String> resultMap = WechatUtil.createMemberCard(wechatMemberCard);
        System.out.println(GSON.toJson(resultMap));
    }
}