package cn.jokeo.lovepig.entity.vo;

import cn.jokeo.lovepig.entity.UserBase;
import lombok.Data;

/**
 * 登录信息
 *
 * @author joke
 * @date 2021/8/12 11:40
 */
@Data
public class LoginTokenVo {
    /**
     * 用户token
     */
    private String token;
    /**
     * 是否是新用户
     */
    private boolean isNewUser = false;
    /**
     * 没有创建恋爱空间
     */
    private boolean notLoveStart = false;
    /**
     * 已经创建恋爱空间了但是还是单身
     */
    private boolean isSingle = false;
    /**
     * 登录的用户信息
     */
    private UserVo loginUser;
}
