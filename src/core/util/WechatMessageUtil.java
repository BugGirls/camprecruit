package core.util;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
<<<<<<< HEAD
import java.util.*;
=======
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> merge project

/**
 * 微信消息处理工具
 *
 * @author Hystar
 * @date 2018/7/23
 */
public class WechatMessageUtil {

    // 各种消息类型,除了扫带二维码事件
    /**
     * 文本消息
     */
    public static final String MESSAGE_TEXT = "text";
    /**
     * 图片消息
     */
    public static final String MESSAtGE_IMAGE = "image";
    /**
     * 图文消息
     */
    public static final String MESSAGE_NEWS = "news";
    /**
     * 语音消息
     */
    public static final String MESSAGE_VOICE = "voice";
    /**
     * 视频消息
     */
    public static final String MESSAGE_VIDEO = "video";
    /**
     * 小视频消息
     */
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";
    /**
     * 地理位置消息
     */
    public static final String MESSAGE_LOCATION = "location";
    /**
     * 链接消息
     */
    public static final String MESSAGE_LINK = "link";
    /**
     * 事件推送消息
     */
    public static final String MESSAGE_EVENT = "event";

    /**
     * 审核事件通过推送
     */
    public static final String MESSAGE_EVENT_CARD_PASS_CHECK = "card_pass_check";
    /**
     * 审核事件未通过推送
     */
    public static final String MESSAGE_EVENT_CARD_NOT_PASS_CHECK = "card_not_pass_check";

    /**
     * 卡券领取事件推送
     */
    public static final String MESSAGE_EVENT_USER_GET_CARD = "user_get_card";

    /**
     * 卡券转赠事件推送
     */
    public static final String MESSAGE_EVENT_USER_GIFTING_CARD = "user_gifting_card";

    /**
     * 删除卡券事件推送
     */
    public static final String MESSAGE_EVENT_USER_DEL_CARD = "user_del_card";

    /**
     * 卡券核销事件推送
     */
    public static final String MESSAGE_EVENT_USER_CONSUME_CARD = "user_consume_card";

    /**
     * 微信买单事件推送
     */
    public static final String MESSAGE_EVENT_USER_PAY_FROM_PAY_CELL = "User_pay_from_pay_cell";

    /**
     * 新建门店审核通过的事件推送
     */
    public static final String MESSAGE_EVENT_POI_CHECK_NOTIFY = "poi_check_notify";

    /**
     * 关注公众号
     */
    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";

    /**
     * 取消关注公众号
     */
    public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";


    /**
     * 将xml 转换成Map集合
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        SAXReader reader = new SAXReader();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Element root = document.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 文本消息转化为xml
     *
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);

    }

    /**
     * 将微信消息中的CreateTime转换成标准格式的时间(yyyy-MM-dd HH:mm:ss)
     *
     * @param createTime
     * @return
     */
    public static String formatTime(String createTime) {
        // 将微信传入的CreateTime转换成long类型,再乘以1000
        long msgCreateTime = Long.parseLong(createTime) * 1000L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(msgCreateTime));
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
<<<<<<< HEAD

    /**
     * 所有map集合中的参数按照字段名的 ascii 码值从小到大排序(升序)
     *
     * @param map
     * @return
     */
    public static Map<String, Object> sort(Map<String, Object> map) {
        Map<String, Object> result = new LinkedHashMap<>(16);

        if (map == null || map.isEmpty()) {
            return result;
        }

        Collection<String> keySet = map.keySet();
        List<String> list = new ArrayList<>(keySet);
        Collections.sort(list);
        list.forEach(x -> result.put(x, map.get(x)));

        return result;
    }

    /**
     * 将传入的Map集合以&符号进行拼接，返回拼接后的字符串
     *
     * @param map
     * @return
     */
    public static String assembleMap(Map<String, Object> map) {
        StringBuffer stringBufferParam = new StringBuffer();
        for (String key : map.keySet()) {
            stringBufferParam.append(key + "=" + map.get(key) + "&");
        }
        String stringBuffer = stringBufferParam.substring(0, stringBufferParam.length() - 1);
        return stringBuffer;
    }

=======
>>>>>>> merge project
}
