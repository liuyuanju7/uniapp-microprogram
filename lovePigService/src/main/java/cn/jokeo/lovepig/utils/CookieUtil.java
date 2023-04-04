package cn.jokeo.lovepig.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取cookie中的数据
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/28 14:42
 */
public class CookieUtil {
    public static String getValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

}
