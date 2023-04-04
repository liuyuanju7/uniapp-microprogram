package cn.jokeo.lovepig.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.config.WxMaConfiguration;
import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.UserLogin;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.entity.request.WechatLoginRequest;
import cn.jokeo.lovepig.entity.vo.LoginTokenVo;
import cn.jokeo.lovepig.entity.vo.UserVo;
import cn.jokeo.lovepig.mapper.UserLoginMapper;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.service.IUserBaseService;
import cn.jokeo.lovepig.service.IUserLoginService;
import cn.jokeo.lovepig.utils.LovePigUtil;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements IUserLoginService {

    @Resource
    private IUserBaseService userBaseService;
    @Resource
    private TokenService tokenService;

    @Override
    public R<LoginTokenVo> preWechatLogin(String appid, String code) {
        return null;
    }

    @Override
    public R<LoginTokenVo> wechatLogin(WechatLoginRequest wechatLoginRequest) {
        String openid = getOpenid(wechatLoginRequest);
        LambdaQueryWrapper<UserLogin> wrapper = Wrappers.lambdaQuery(UserLogin.class);
        wrapper.eq(UserLogin::getOpenid, openid);
        UserLogin userLogin = baseMapper.selectOne(wrapper);
        LoginTokenVo loginTokenVo = new LoginTokenVo();
        //如果查不到说明用户没有注册,直接返回调用注册逻辑
        if (userLogin == null) {
            loginTokenVo.setNewUser(true);
            return R.ok(loginTokenVo);
        }
        //登录逻辑
        login(loginTokenVo, userLogin);
        //判断感情状态
        loginTokenVo.setNotLoveStart(loginTokenVo.getLoginUser().getLoveId() == 0);
        loginTokenVo.setSingle(loginTokenVo.getLoginUser().getSpouseId() == 0);
        return R.ok(loginTokenVo);
    }


    private UserLogin getUserWithWechat(WechatLoginRequest wechatLoginRequest) {
        if (StringUtils.isBlank(wechatLoginRequest.getCode())) {
            return null;
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(wechatLoginRequest.getAppid());
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(wechatLoginRequest.getCode());
            String openid = session.getOpenid();
            LambdaQueryWrapper<UserLogin> wrapper = Wrappers.lambdaQuery(UserLogin.class);
            wrapper.eq(UserLogin::getOpenid, openid);
            return baseMapper.selectOne(wrapper);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    private void login(LoginTokenVo loginTokenVo, UserLogin userLogin) {
        UserBase userBaseInfo = userBaseService.getById(userLogin.getUserId());
        UserVo userVo = BeanUtil.copyProperties(userBaseInfo, UserVo.class);
        String token = tokenService.createToken(userVo);
        loginTokenVo.setToken(token);
        loginTokenVo.setLoginUser(userVo);
    }

    @Override
    @Transactional
    public R<LoginTokenVo> wechatRegister(WechatLoginRequest wechatLoginRequest) {
        String openid = getOpenid(wechatLoginRequest);

        //1.查询用户是否已经注册过了,注册过直接返回 todo 需要优化..
        LambdaQueryWrapper<UserLogin> wrapper = Wrappers.lambdaQuery(UserLogin.class);
        wrapper.eq(UserLogin::getOpenid, openid);
        UserLogin userLogin = baseMapper.selectOne(wrapper);
        //如果查不到说明用户没有注册,直接返回调用注册逻辑
        LoginTokenVo loginTokenVo = new LoginTokenVo();
        if (userLogin != null) {
            login(loginTokenVo, userLogin);
            return R.ok(loginTokenVo);
        }

        //2.创建用户信息
        String userName = LovePigUtil.genUserName();
        //插入 微信用户数据
        UserBase userBase = new UserBase();
        BeanUtil.copyProperties(wechatLoginRequest, userBase);
        userBase.setUsername(userName);
        boolean save = userBaseService.save(userBase);
        //插入 登录数据
        UserLogin newUserLogin = new UserLogin();
        newUserLogin.setUserId(userBase.getId());
        newUserLogin.setOpenid(openid);
        newUserLogin.setUsername(userName);
        boolean saveUserLogin = save(newUserLogin);
        //插入失败说明有问题
        if (!(save && saveUserLogin)) {
            throw new LoveException(LoveErrorEnum.USER_REGISTER_FAIL);
        }
        //登录逻辑
        login(loginTokenVo, newUserLogin);
        //注册情况下就是单身
        loginTokenVo.setNotLoveStart(true);
        loginTokenVo.setSingle(true);
        return R.ok(loginTokenVo);
    }

    private String getOpenid(WechatLoginRequest wechatLoginRequest) {
        final WxMaService wxService = WxMaConfiguration.getMaService(wechatLoginRequest.getAppid());
        String openid = null;
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(wechatLoginRequest.getCode());
            openid = session.getOpenid();
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        if (StrUtil.isBlank(openid)) {
            throw new LoveException(LoveErrorEnum.USER_WX_OPENID);
        }
        return openid;
    }
}
