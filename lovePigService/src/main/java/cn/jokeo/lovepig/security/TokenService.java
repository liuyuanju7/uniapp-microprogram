package cn.jokeo.lovepig.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.vo.UserVo;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.utils.Constants;
import cn.jokeo.lovepig.utils.RedisConstants;
import cn.jokeo.lovepig.utils.RedisUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
@Getter
public class TokenService {
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认7天）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long ONE_DAY = 24 * 60 * 60 * 1000L;
    /**
     * jwt用户标识信息key
     */
    private static final String CLAIM_USER_UUID_KEY = "userUUID";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 检验token正确性获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserInfo getLoginUserInfo(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            try {
                Map<String, Claim> claims = parseToken(token);
                String userUuid = claims.get(CLAIM_USER_UUID_KEY).asString();
                String wxLoginKey = RedisConstants.getWxLoginKey(userUuid);
                return (LoginUserInfo) redisUtil.get(wxLoginKey);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 通过token获取用户信息
     *
     * @param token
     * @return
     */
    public LoginUserInfo getLoginUserInfo(String token) {
        // 获取请求携带的令牌
        if (StrUtil.isNotEmpty(token)) {
            if (token.startsWith(Constants.WX_TOKEN_PREFIX)) {
                token = token.replace(Constants.WX_TOKEN_PREFIX, "");
            }
            try {
                Map<String, Claim> claims = parseToken(token);
                String userUuid = claims.get(CLAIM_USER_UUID_KEY).asString();
                String wxLoginKey = RedisConstants.getWxLoginKey(userUuid);
                return (LoginUserInfo) redisUtil.get(wxLoginKey);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 解析token并获得载体
     *
     * @param token
     * @return
     */
    public Map<String, Claim> parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        return verify.getClaims();
    }

    /**
     * 用户信息变动时设置用户身份信息
     *
     * @param loginUserInfo
     */
    public void updateLoginUserInfo(LoginUserInfo loginUserInfo) {
        if (loginUserInfo != null) {
            refreshToken(loginUserInfo);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUserInfo(String userUuid) {
        if (userUuid != null) {
            String userKey = RedisConstants.getWxLoginKey(userUuid);
            redisUtil.del(userKey);
        }
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUserInfo
     * @return 令牌
     */
    public void verifyToken(LoginUserInfo loginUserInfo) {
        long expireTime = loginUserInfo.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= ONE_DAY) {
            refreshToken(loginUserInfo);
        }
    }

    /**
     * 刷新令牌有效期
     * 将用户登录信息存放到redis用来控制登录状态
     *
     * @param loginUserInfo 登录信息
     */
    public void refreshToken(LoginUserInfo loginUserInfo) {
        //修改失效时间
        loginUserInfo.setLoginTime(System.currentTimeMillis());
        DateTime expireDay = DateUtil.offsetDay(new Date(), expireTime);
        loginUserInfo.setExpireTime(expireDay.getTime());
        // 根据uuid将LoginUserInfo缓存
        String userKey = RedisConstants.getWxLoginKey(loginUserInfo.getUuid());
        redisUtil.set(userKey, loginUserInfo, expireTime, TimeUnit.DAYS);
    }


    /**
     * 创建令牌
     *
     * @param userVo 用户信息
     * @return 令牌
     */
    public String createToken(UserVo userVo) {
        LoginUserInfo loginUserInfo=new LoginUserInfo();
        loginUserInfo.setUser(userVo);
        loginUserInfo.setUuid(UUID.randomUUID(false).toString());
        refreshToken(loginUserInfo);
        return LoginChannelEnum.WECHAT.getPrefix() + createToken(loginUserInfo.getUuid());
    }

    /**
     * 从数据声明生成令牌
     * 创建jwt就放一个userid
     *
     * @param userUuid 用户标识
     * @return 令牌
     */
    private String createToken(String userUuid) {
        return JWT.create()
                .withClaim(CLAIM_USER_UUID_KEY, userUuid)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 获取到请求头中的token
     * todo 处理不同的渠道
     *
     * @param request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StrUtil.isNotEmpty(token) && token.startsWith(LoginChannelEnum.WECHAT.getPrefix())) {
            token = token.replace(LoginChannelEnum.WECHAT.getPrefix(), "");
        }
        return token;
    }

}
