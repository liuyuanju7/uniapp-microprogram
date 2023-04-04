package cn.jokeo.lovepig.security.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录逻辑处理抽象类
 *
 * @author joke
 * @date 2021/8/5 17:52
 */
public abstract class LoginProcessAbstract {
    /**
     * 初始化逻辑处理
     */
    public void login(HttpServletRequest request, HttpServletResponse response, Object handler) {
        loginInfoProcess(request, response, handler);
        pigLoginProcess(request, response, handler);
        pigRolesProcess(request, response, handler);
    }

    /**
     * 登录信息处理
     */
    abstract void loginInfoProcess(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 登录状态处理=判断登录状态
     */
    abstract void pigLoginProcess(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 用户角色权限处理
     */
    abstract void pigRolesProcess(HttpServletRequest request, HttpServletResponse response, Object handler);

}
