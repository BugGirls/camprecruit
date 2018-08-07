package core.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static long DAY = 86400000;
    public static long HOUR = 3600000;
    public static long SECOND = 1000; //秒
    public static String SALT = "hndt.com"; //加盐

    public static int COOKIE_EXPIRE_SECOND = 30 * 60;
    public static String SESSION_MANAGER_KEY = "manager";
    public static String COOKIE_LOGIN_ID = "LI";

    //互动中心
    public static String INTERACTIVE_HOST="http://talk.hndt.com";
    public static String GET_USER_COUNT_URL="/UC/api/user/getUserNumInfo.do";
    public static String APP_ID="828c2a9c9ad609ff25bccf1732f6721d";
    public static String SECRET="6a866b260fe600c5100e700348a997b23429ab22";

    //public
    public static final DateFormat DATEFORMAT1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DATEFORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DATEFORMAT3 = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATEFORMAT4 = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat DATEFORMAT5 = new SimpleDateFormat("yyyyMM");
    public static final DateFormat DATEFORMAT_ZH1 = new SimpleDateFormat("yyyy年MM月dd HH:mm");
    public static final DateFormat DATEFORMAT_ZH2 = new SimpleDateFormat("yyyy年MM月dd");

    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
    public static final String REGEX_EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-zd]+[-.])+[A-Za-z\\d]{2,5}$";
    public static final String REGEX_EMAIL2 = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    //private
    private static DateFormat DF_FORM_ID = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL2, email);
    }

    public static String nowTimeString() {
        return DATEFORMAT1.format(new Date());
    }

    public static String nowDateString() {
        return DATEFORMAT4.format(new Date());
    }

    public static Timestamp currentTimestamp() {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        stamp.setNanos(0);
        return stamp;
    }

    public static String currentTimeString(DateFormat format) {
        return format.format(new Date());
    }

    public synchronized static Timestamp parseFromString(String time, DateFormat format) {
        Timestamp timestamp = null;
        try {
            timestamp = new Timestamp(format.parse(time).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    //把不带[]的字符串转为集合，限于小写逗号
    public static List<String> asList(String text) {
        if (Utils.isNotEmpty(text)) {
            String[] words = StringUtils.split(text, ',');
            List<String> list = Arrays.asList(words);
            return list;
        }
        return null;
    }

    //用正则分割
    public static List<String> split(String line) {
        String[] words = line.split("[\\s*|；|,|，|;|.]");
        List<String> words_list = new ArrayList<>(Arrays.asList(words));
        words_list.removeIf(x -> Utils.isEmpty(x));
        return words_list;
    }

    public static String asString(List list) {
        return StringUtils.join(list, ',');
    }

    public static double round(Double val, int precision) {
        BigDecimal bd = new BigDecimal(val).setScale(precision, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static double round(Double val) {
        BigDecimal bd = new BigDecimal(val).setScale(1, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static String md5(String str) {
        String result = str;
        try {
            if (isNotEmpty(str)) {
                result = DigestUtils.md5DigestAsHex(str.getBytes());
            }
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        return result;
    }

    private static void removeNull(Collection list) {
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            if (it.next() == null) {
                it.remove();
            }
        }
    }

    private static boolean isNotEmpty(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof String) {
            String temp = o.toString().trim();
            return temp.length() > 0;
        } else if (o instanceof Integer) {
            return ((Integer) o) != 0L;
        } else if (o instanceof Long) {
            return ((Long) o) != 0L;
        } else if (o instanceof Double) {
            return ((Double) o) != 0D;
        } else if (o instanceof Float) {
            return ((Float) o) != 0;
        } else if (o instanceof Collection) {
            Collection temp = (Collection) o;
            removeNull(temp);
            return !(temp.isEmpty());
        } else return o != null;
    }

    private static boolean isEmpty(Object o) {
        return !isNotEmpty(o);
    }

    /**
     * 参数全部不为 [null、0、''] 时,返回真。
     */
    public static boolean isNotEmpty(Object... o) {
        boolean notEmpty = true;
        try {
            for (Object object : o) {
                if (isEmpty(object)) {
                    notEmpty = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notEmpty;
    }

    /**
     * 参数任意一个为 [null、0、''] 时,返回真。
     */
    public static boolean isEmpty(Object... o) {
        return !isNotEmpty(o);
    }

    public static HashMap asHashMap(Object... params) {
        if (params.length == 0 || params.length % 2 != 0) {

        }
        HashMap map = new HashMap(params.length / 2);
        for (int i = 0; i < params.length; i = i + 2) {
            map.put(params[i], params[i + 1]);
        }
        return map;
    }

    public static String asJsonMap(Object... params) {
        Map map = asHashMap(params);
        return GSON.getInstance().create().toJson(map);
    }

    public static String asJsonArray(Object... params) {
        return GSON.getInstance().create().toJson(params);
    }

    public static List<String> find(String text, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 用于打印输出
     *
     * @param request
     * @return
     */
    public static void printParameters(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            sb.append(key).append("=").append(request.getParameter(key));
            sb.append("; ");
        }
        System.out.println(sb.toString());
    }

    public static void println(Object... objs) {
        System.out.println(Utils.asJsonArray(objs));
    }

    public static String sha1(String src) {
        String obj = src;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(src.getBytes());
            Formatter formatter = new Formatter();
            for (byte b : digest) {
                formatter.format("%02x", b);
            }
            obj = formatter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String sha256(String src) {
        String obj = src;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(src.getBytes());
            Formatter formatter = new Formatter();
            for (byte b : digest) {
                formatter.format("%02x", b);
            }
            obj = formatter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String urlEncode(String url) {
        String url_en = url;
        try {
            url_en = URLEncoder.encode(url, "utf-8");
        } catch (Exception e) {
        }
        return url_en;
    }

    public static String requestIp(HttpServletRequest request) {
        //depends on 部署环境
        String ip = null;
        if (request != null) {
            ip = request.getHeader("x-real-ip");
            if (ip == null) {
                ip = request.getHeader("CLIENT-IP");
            }
            if (ip == null) {
                ip = request.getHeader("X-FORWARDED-FOR");
            }
            if (ip == null) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    public static String urlDecode(String url) {
        String url_de = url;
        try {
            url_de = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url_de;
    }

    public static boolean isNumber(String text) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(text);
        return match.matches();
    }

    public static boolean isMobile(String tel) {
        return Utils.isNotEmpty(tel) && (tel.startsWith("1")) && isNumber(tel) && tel.length() == 11;
    }

    public static String base64UrlEncode(String text){
        String result=null;
        try {
            byte[] b=org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(text.getBytes(CharEncoding.UTF_8));
            result=new String(b,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String base64UrlDecode(String text){
        String result=null;
        try {
            byte[] b= org.apache.commons.codec.binary.Base64.decodeBase64(text.getBytes(CharEncoding.UTF_8));
            result=new String(b, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String base64Encode(String text) {
        String t_text = null;
        try {
            if (Utils.isNotEmpty(text)) {
                t_text = new String(Base64.getEncoder().encode(text.getBytes("utf-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t_text;
    }

    public static String base64Decode(String text) {
        String t_text = null;
        try {
            if (Utils.isNotEmpty(text)) {
                t_text = new String(Base64.getDecoder().decode(text.getBytes("utf-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t_text;
    }


    /**
     * 以key为密钥,把src加密,返回密文的base64表现形式
     * <p>
     * String key = "JinTianHaoYun7@LaoLangQingChiJi";
     * String data = "9";
     * System.out.println("要加密的字符串:" + data);
     * String result = (Utils.desEncrypt(data, key));
     * System.out.println("加密后:" + result);
     * String result2 = Utils.desDecrypt(result, key);
     * System.out.println("解密后:" + result2);
     */
    public static String desEncrypt(String src, String key) {
        try {
            final String method = "DES";
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(method);
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(method);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(src.getBytes());
            return new String(Base64.getEncoder().encode(result));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * src是base64表示的密文
     * 以key为密钥,把src解密,返回明文字符串
     */
    public static String desDecrypt(String src, String key) {
        try {
            final String method = "DES";
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(method);
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(method);
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            return new String(cipher.doFinal(Base64.getDecoder().decode(src)));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        String value2 = base64Encode(value);
        Cookie cookie = new Cookie(name, value2);
        cookie.setPath("/");
        cookie.setDomain(".hnr.cn");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie == null ? null : base64Decode(cookie.getValue());
    }

    static final String[] mobileDevices = new String[]{"Android", "iPhone", "iPod", "iPad", "UC", "Windows Phone", "webOS", "BlackBerry"};

    public static boolean ifMobileReq(HttpServletRequest request) {
        boolean yesno = false;
        String agent = request.getHeader("User-Agent");
        for (String device : mobileDevices) {
            if (agent.contains(device)) {
                request.getSession().setAttribute("mobile", "m");
                yesno = true;
                break;
            }
        }
        return yesno;
    }

    public static String urlWithComponent(Object... words) {
        StringBuilder sb = new StringBuilder();
        for (Object str : words) {
            if (sb.length() == 0) {
                sb.append(str);
            } else {
                if (sb.charAt(sb.length() - 1) != '/') {
                    sb.append('/');
                }
                String str_t = String.valueOf(str);
                if (str_t.indexOf('/') == 0) {
                    str_t = str_t.substring(1, str_t.length());
                }
                sb.append(String.valueOf(str_t));
            }
        }
        return sb.toString();
    }


    public static String convertDotString(String content) {
        String result = "";
        if (content.contains("，")) {
            result = content.replaceAll("，", ",");
        }
        if (content.contains(";")) {
            result = content.replaceAll(";", ",");
        }
        if (Utils.isEmpty(result)) {
            result = content.replace(" ", ",");
        }
        return result;
    }

    public static String suffixRandom(String url) {
        String patameter_url = "";
        if (Utils.isEmpty(url)) {
            return patameter_url;
        }
        String random = String.valueOf(System.currentTimeMillis() / 1000);
        char c1 = '?', c2 = '&';
        StringBuilder sb = new StringBuilder(url);
        int idx = url.lastIndexOf(c1);
        int idx2 = url.lastIndexOf(c2);
        if (idx < 0 && idx2 < 0) {
            sb.append(c1).append(random);
        } else if (idx == sb.length() - 1 || idx2 == sb.length() - 1) {
            sb.append(random);
        } else if (idx > 0) {
            sb.append(c2).append(random);
        }
        patameter_url = sb.toString();

        return patameter_url;
    }

    public static boolean urlIsUsage(String url) {
        boolean isUsage = true;
        URL test = null;
        if (Utils.isEmpty(url) || !url.contains("http")) {
            isUsage = false;
        }
        try {
            test = new URL(url);
        } catch (MalformedURLException e) {
            isUsage = false;
        }
        if (isUsage) {
            HttpURLConnection con = null;
            int state = 0;
            try {
                con = (HttpURLConnection) test.openConnection();
                if (con != null) {
                    state = con.getResponseCode();
                    isUsage = state == 200;
                } else {
                    isUsage = false;
                }
            } catch (IOException e) {
                isUsage = false;
            }
        }
        return isUsage;
    }

    public static String get(OkHttpClient client, String url){
        String result=null;
        if (client==null){
            client=new OkHttpClient.Builder().build();
        }
        try {
            Request request=new Request.Builder().url(url).get().build();
            Response response=client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            result=Utils.asJsonMap("status",0,"msg",e.getMessage());
        }
        return result;
    }
}
