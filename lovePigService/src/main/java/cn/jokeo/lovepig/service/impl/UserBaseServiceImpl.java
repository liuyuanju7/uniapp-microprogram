package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.vo.UserVo;
import cn.jokeo.lovepig.mapper.UserBaseMapper;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.model.LoginUserInfo;
import cn.jokeo.lovepig.service.IUserBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase> implements IUserBaseService {
    @Resource
    private TokenService tokenService;

    @Override
    public Boolean updateUserById(UserBase userBase) {
        int i = baseMapper.updateById(userBase);
        UserBase newUserBase = baseMapper.selectById(userBase.getId());
        LoginUserInfo loginUserInfo=UserHolder.getUser();
        UserVo userVo = BeanUtil.copyProperties(userBase, UserVo.class);
        //修改用户信息
        loginUserInfo.setUser(userVo);

        tokenService.updateLoginUserInfo(loginUserInfo);
        return i == 1;
    }
}
