package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.LoveStart;
import cn.jokeo.lovepig.entity.UserBase;
import cn.jokeo.lovepig.entity.request.AcceptLoveRequest;
import cn.jokeo.lovepig.entity.request.CreateLoveStarRequest;
import cn.jokeo.lovepig.entity.request.QueryResourcesRequest;
import cn.jokeo.lovepig.entity.vo.CreateLoveStartVo;
import cn.jokeo.lovepig.entity.vo.LoveStartVo;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.mapper.LoveStartMapper;
import cn.jokeo.lovepig.mapper.ResourceRelateMapper;
import cn.jokeo.lovepig.mapper.UserBaseMapper;
import cn.jokeo.lovepig.security.TokenService;
import cn.jokeo.lovepig.service.ILoveResourcesService;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.service.IUserBaseService;
import cn.jokeo.lovepig.utils.PromiseDateUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.cache.LoveStartCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Service
public class LoveStartServiceImpl extends ServiceImpl<LoveStartMapper, LoveStart> implements ILoveStartService {

    @Resource
    private UserBaseMapper userBaseMapper;
    @Resource
    private IUserBaseService userBaseService;

    @Resource
    private LoveStartCache loveStartCache;
    @Resource
    private ILoveResourcesService resourceService;
    @Resource
    private ResourceRelateMapper resourceRelateMapper;
    @Resource
    private TokenService tokenService;

    @Override
    public R<LoveStartVo> queryLoveStartInfo(Long loveId) {
        if (loveId == null) {
            return R.fail(521, "请提供给恋爱空间id");
        }
        LoveStart loveStart = baseMapper.selectById(loveId);
        if (loveStart == null) {
            return R.fail(521, "没有找到指定的恋爱回忆空间");
        }
        //查询关联的资源
        LoveStartVo loveStartVo = BeanUtil.copyProperties(loveStart, LoveStartVo.class);
        UserBase inviteUser = userBaseMapper.selectById(loveStart.getInviteUser());
        inviteUser.setNickname(EmojiParser.parseToUnicode(inviteUser.getNickname()));
        if (loveStart.getAcceptedUser() != 0) {
            UserBase acceptedUser = userBaseMapper.selectById(loveStart.getAcceptedUser());
            acceptedUser.setNickname(EmojiParser.parseToUnicode(acceptedUser.getNickname()));
            loveStartVo.setAcceptedUserInfo(acceptedUser);
        }
        List<ResourceVo> resources = resourceService.queryBindResources(new QueryResourcesRequest(loveId, loveId, 2, null, false));
        loveStartVo.setInviteUserInfo(inviteUser);
        loveStartVo.setLoveDay(PromiseDateUtil.promiseLastDay(loveStartVo.getLoveTime()) + 1);
        loveStartVo.setBanners(resources);
        return R.ok(loveStartVo);
    }

    @Override
    public List<Long> queryHost(Long loveId) {
        LoveStartVo loveStart = loveStartCache.get(loveId);
        if (loveStart == null) {
            return null;
        }
        List<Long> hosts = new ArrayList<>();
        hosts.add(loveStart.getInviteUser());
        hosts.add(loveStart.getAcceptedUser());
        return hosts;
    }

    @Override
    public Long queryObject(Long loveId, Long myUserId) {
        List<Long> hosts = queryHost(loveId);
        return myUserId.equals(hosts.get(0)) ? hosts.get(1) : hosts.get(0);
    }

    @Override
    public R changeRoomWallpaper(String wallpaper, Long loveId) {


        return null;
    }


    @Override
    @Transactional
    public R<CreateLoveStartVo> createLoveStart(CreateLoveStarRequest request) {
        LoveStart loveStart = BeanUtil.copyProperties(request, LoveStart.class);
        int insert = baseMapper.insert(loveStart);

        //更新用户信息

        UserBase userBase = new UserBase();
        userBase.setId(request.getInviteUser());
        userBase.setLoveId(loveStart.getLoveId());
        userBaseService.updateUserById(userBase);
        //初始化空间数据


        CreateLoveStartVo createLoveStartVo = new CreateLoveStartVo();
        createLoveStartVo.setLoveId(loveStart.getLoveId());
        return R.result(insert).setData(createLoveStartVo);
    }

    @Override
    @Transactional
    public R<Boolean> acceptLove(AcceptLoveRequest request) {
        LoveStart loveStart = new LoveStart();
        loveStart.setLoveId(request.getLoveId());
        loveStart.setLoveState(1);
        loveStart.setAcceptedUser(request.getAcceptUser());
        loveStart.setAcceptedTime(new Date());
        int i = baseMapper.updateById(loveStart);
        //更新恋爱空间信息

        //更新用户信息=创建者
        UserBase inviteUser = new UserBase();
        inviteUser.setId(request.getInviteUser());
        inviteUser.setSpouseId(request.getAcceptUser());
        userBaseService.updateUserById(inviteUser);

        //更新用户信息=接受者
        UserBase acceptUser = new UserBase();
        acceptUser.setId(request.getAcceptUser());
        acceptUser.setSpouseId(request.getInviteUser());
        acceptUser.setLoveId(request.getLoveId());
        userBaseService.updateUserById(acceptUser);

        return R.result(i);
    }

    @Override
    public boolean isOwner(Long loveId, Long userId) {
        LoveStartVo loveStart = loveStartCache.get(loveId);
        if (loveStart == null) {
            return false;
        }
        return loveStart.getInviteUser().equals(userId) || loveStart.getAcceptedUser().equals(userId);
    }
}
