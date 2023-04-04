package cn.jokeo.lovepig.security.model;

import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.vo.UserVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 登录的用户信息
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserLogin对象", description = "")
public class LoginUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 拥有的空间id
     */
    private Long loveId;
    /**
     * 用户唯一标识
     */
    private String uuid;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 用户基本信息
     */
    private UserVo user;

    public void setUser(UserVo user) {
        this.user = user;
        this.userId = user.getId();
        this.loveId = user.getLoveId();
    }
}
