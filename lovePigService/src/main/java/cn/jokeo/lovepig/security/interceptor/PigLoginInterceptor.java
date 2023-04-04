package cn.jokeo.lovepig.security.interceptor;


import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.annotation.PigLogin;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 配合注解实现登录权限控制
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/28 14:39
 */
public class PigLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PigLogin annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(PigLogin.class);
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }
        LoginUserInfo user = UserHolder.getUser();
        if (user == null) {
            throw new LoveException(LoveErrorEnum.USER_NO_LOGIN);
        }
        return true;
    }
}
