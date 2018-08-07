package core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author Hystar
 * @date 2018/7/18
 */
public class WechatPayUtil {

    /**
     * 生成32为随机字符串
     *
     * @return
     */
    public static String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }

    public static String getXML(Map map) {
        Iterator iterator = map.keySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer _stringBuffer = new StringBuffer();
        while (iterator.hasNext()) {
            String _key = iterator.next().toString();
            String _value = map.get(_key).toString();
            _stringBuffer.append("<" + _key + ">" + _value + "</" + _key + ">");
        }

        stringBuffer.append(" <xml> ");
        stringBuffer.append("<sign>" + sign(map) + "</sign>");
        stringBuffer.append(_stringBuffer);
        stringBuffer.append(" </xml> ");

        return stringBuffer.toString();
    }

    private static String sign(Map map) {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        List list = new ArrayList<>();
        while (iterator.hasNext()) {
            String _key = iterator.next().toString();
            if (("".equals(map.get(_key))) || (null == map.get(_key))) {
                continue;
            }
            if ("sign".equals(_key)) {
                continue;
            }
            list.add(_key);
        }

        Object[] objects = list.toArray();
        Arrays.sort(objects);

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objects.length; i++) {
            stringBuffer.append(objects[i] + "=" + map.get(objects[i]) + "&");
        }

        stringBuffer.append("key=" + PropertiesUtil.getProperty("WECHAT_MCH_KEY"));

        return md5(stringBuffer.toString());
    }

    private static String md5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(s.getBytes("UTF-8"));
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                int val = ((int) bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String submit(String xml, String path) {
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            stringBuffer = new StringBuffer();
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(xml);
            writer.flush();
            writer.close();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.disconnect();

        return stringBuffer.toString();
    }

}
