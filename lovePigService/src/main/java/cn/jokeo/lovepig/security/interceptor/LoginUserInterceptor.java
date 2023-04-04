package cn.jokeo.lovepig.security.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.security.LoginChannelEnum;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.process.LoginProcessAbstract;
import cn.jokeo.lovepig.utils.Constants;
import cn.jokeo.lovepig.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 用来获取用户的登录信息存放到request中
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/28 14:39
 */
public class LoginUserInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(tokenService.getHeader());
        if (StrUtil.isBlank(token)) {
            return true;
        }
        //调用不同端的处理逻辑
        LoginProcessAbstract process = getProcess(token);
        process.login(request, response, handler);
        return true;
    }

    private LoginProcessAbstract getProcess(String token) {
        int index = token.indexOf(Constants.TOKEN_JOIN);
        String substring = token.substring(0, index + 1);
        Class processName = LoginChannelEnum.channelMap.get(substring);
        if (processName == null) {
            throw new LoveException(LoveErrorEnum.LOGIN_CHANNEL);
        }
        return (LoginProcessAbstract) SpringBeanUtil.getBean(processName);
    }
}
