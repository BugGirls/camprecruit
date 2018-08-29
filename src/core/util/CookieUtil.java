package core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类可以从浏览器请求中提取出cookies并进行对cookies的相关操作
 *
 * @author Hystar
 * @date 2018/8/27
 */
public class CookieUtil {

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存cookie
     *
     * @param response
     * @param name
     * @param value
     * @param time
     * @return
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) {
        // 创建一个cookie对象，键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置cookie有效期
        cookie.setMaxAge(time);
        // 将cookie添加到response中，使之生效；addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        response.addCookie(cookie);
        return response;
    }

    public static void deleteCookieByName(HttpServletRequest request, HttpServletResponse response, String deleteKey) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        for (String key:cookieMap.keySet()) {
            if (key == deleteKey && key.equals(deleteKey)) {
                Cookie cookie = cookieMap.get(key);
                // 设置cookie有效时间为0
                cookie.setMaxAge(0);
                // 不设置存储路径
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
}
