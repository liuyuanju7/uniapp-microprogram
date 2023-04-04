package cn.jokeo.lovepig.security.interceptor;


import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.entity.enums.RoleEnum;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.annotation.PigRoles;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.utils.request.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录角色拦截
 * 配合注解实现登录权限控制
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/28 14:39
 */
public class PigRolesInterceptor implements HandlerInterceptor {
    @Autowired
    private ILoveStartService loveStartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PigRoles annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(PigRoles.class);
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
        RoleEnum role = annotation.role();
        String loveId = RequestUtil.getParameter(request, "loveId");
        // String loveId ="521";
        if (loveId == null) {
            throw new LoveException(LoveErrorEnum.NOT_LOVE_ID);
        }
        boolean owner = loveStartService.isOwner(Long.parseLong(loveId), user.getUserId());
        //如果需要是OWNER权限  并且当前登录的用户拥有owner===取反
        if (!(role == RoleEnum.OWNER && owner)) {
            throw new LoveException(403, "无权访问!");
        }
        return true;
    }
}
