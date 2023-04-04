package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.UserBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
public interface IUserBaseService extends IService<UserBase> {
    /**
     * 修改用户信息,并且更新用户在redis的信息
     *
     * @param userBase
     * @return
     */
    Boolean updateUserById(UserBase userBase);

}
