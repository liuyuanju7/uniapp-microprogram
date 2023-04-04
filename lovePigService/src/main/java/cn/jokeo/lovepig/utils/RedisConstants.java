package cn.jokeo.lovepig.utils;

/**
 * redis的key
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/25 20:51
 */
public interface RedisConstants {
    public static final String WX_LOGIN_KEY = "wx_login_";

    static String getWxLoginKey(String uuid) {
        return WX_LOGIN_KEY + uuid;
    }

}
