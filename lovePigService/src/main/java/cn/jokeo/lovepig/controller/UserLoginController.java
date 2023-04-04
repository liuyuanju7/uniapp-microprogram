package cn.jokeo.lovepig.controller;


import cn.jokeo.lovepig.entity.request.WechatLoginRequest;
import cn.jokeo.lovepig.entity.vo.LoginTokenVo;
import cn.jokeo.lovepig.entity.vo.UserVo;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.service.IUserLoginService;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Api(tags = "用户登录")
@RestController
@RequestMapping("/userLogin")
@Slf4j
public class UserLoginController {

    @Resource
    private IUserLoginService userLoginService;
    @Resource
    private TokenService tokenService;

    /**
     * 微信登录登陆接口
     */
    @PostMapping("/wechatLogin")
    @ApiOperation("微信用户登录接口")
    public R<LoginTokenVo> wechatLogin(@RequestBody WechatLoginRequest wechatLoginRequest) {
        return userLoginService.wechatLogin(wechatLoginRequest);
    }

    /**
     * 微信登录登陆接口(注解+登录)
     */
    @PostMapping("/wechatRegistered")
    public R<LoginTokenVo> wechatRegister(@RequestBody WechatLoginRequest wechatLoginRequest) {
        return userLoginService.wechatRegister(wechatLoginRequest);
    }

    @GetMapping("/getLoginUserInfo")
    public R<UserVo> getLoginUserInfo(String token) {
        LoginUserInfo loginUserInfo = tokenService.getLoginUserInfo(token);
        return R.ok(loginUserInfo == null ? null : loginUserInfo.getUser());
    }
}
