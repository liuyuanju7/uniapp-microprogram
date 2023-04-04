package cn.jokeo.lovepig.security.process;

import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author joke
 * @date 2021/8/16 15:26
 */
@Service
public class WechatLoginProcess extends LoginProcessAbstract {
    @Autowired
    private TokenService tokenService;

    @Override
    void loginInfoProcess(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从redis获取登录凭证
        //查询用户是否已经登录
        LoginUserInfo loginUserInfo = tokenService.getLoginUserInfo(request);
        if (loginUserInfo == null) {
            return;
        }
        //每一次都检查以下是否需要续期!
        tokenService.verifyToken(loginUserInfo);
        //存放到线程副本中
        UserHolder.setUsers(loginUserInfo);
    }

    @Override
    void pigLoginProcess(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        PigLogin annotation;
//        if (handler instanceof HandlerMethod) {
//            annotation = ((HandlerMethod) handler).getMethodAnnotation(PigLogin.class);
//        } else {
//            return;
//        }
//        if (annotation == null) {
//            return;
//        }
//        LoginUserInfo user = UserHolder.getUser();
//        if (user == null) {
//            throw new LoveException(403, "请登陆后访问");
//        }
    }

    @Override
    void pigRolesProcess(HttpServletRequest request, HttpServletResponse response, Object handler) {

    }
}
