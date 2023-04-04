package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.UserLogin;
import cn.jokeo.lovepig.entity.request.WechatLoginRequest;
import cn.jokeo.lovepig.entity.vo.LoginTokenVo;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
public interface IUserLoginService extends IService<UserLogin> {
    R<LoginTokenVo> preWechatLogin(String appid, String code);

    /**
     * 微信登录
     * 如果是新用户就不登录了,直接返回提示需要注册
     *
     * @param wechatLoginRequest@return
     */
    R<LoginTokenVo> wechatLogin(WechatLoginRequest wechatLoginRequest);

    R<LoginTokenVo> wechatRegister(WechatLoginRequest wechatLoginRequest);
}
