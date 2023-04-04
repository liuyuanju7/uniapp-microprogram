package cn.jokeo.lovepig.security;

import cn.jokeo.lovepig.security.model.LoginUserInfo;

/**
 * 存储登录用户的副本
 *
 * @author joke
 */
public class UserHolder {

    private static final ThreadLocal<LoginUserInfo> users = new ThreadLocal<>();

    public static void setUsers(LoginUserInfo user) {
        users.set(user);
    }

    public static LoginUserInfo getUser() {
        return users.get();
    }

    public static Long getUserId() {
        LoginUserInfo loginUserInfo = users.get();
        if (loginUserInfo == null) {
            return null;
        }
        return users.get().getUserId();
    }

    public static void clear() {
        users.remove();
    }

}