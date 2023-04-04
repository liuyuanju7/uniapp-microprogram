package cn.jokeo.lovepig.utils.cache;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.vo.LoveStartVo;
import cn.jokeo.lovepig.entity.vo.UserBaseInfoVo;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.utils.R;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author joke
 * @version 1.0
 * @date 2021/8/22 10:51
 */
@Component
public class LoveStartCache {
    public static final Map<Long, LoveStartVo> LOVE_STARTS = new HashMap<>();

    @Resource
    private ILoveStartService loveStartService;

    public static void clearLoveStarts() {
        LOVE_STARTS.clear();
    }

    public void put(Long key, LoveStartVo loveStart) {
        LOVE_STARTS.put(key, loveStart);
    }

    public LoveStartVo get(Long key) {
        LoveStartVo loveStart = LOVE_STARTS.get(key);
        if (loveStart == null) {
            R<LoveStartVo> loveStartVoR = loveStartService.queryLoveStartInfo(key);
            if (loveStartVoR.getCode() != R.SUCCESS) {
                throw new LoveException(loveStartVoR);
            }
            LOVE_STARTS.put(key, loveStartVoR.getData());
        }
        return LOVE_STARTS.get(key);
    }

    /**
     * 获取内容发送的用户
     *
     * @param key
     * @param userId
     * @return
     */
    public UserBaseInfoVo getUser(Long key, Long userId) {
        LoveStartVo loveStart = get(key);
        UserBase userBase = loveStart.getInviteUserInfo();
        if (userId.equals(loveStart.getAcceptedUser())) {
            userBase = loveStart.getAcceptedUserInfo();
        }
        UserBaseInfoVo userBaseInfoVo = new UserBaseInfoVo();
        BeanUtil.copyProperties(userBase, userBaseInfoVo);
        return userBaseInfoVo;
    }
}
