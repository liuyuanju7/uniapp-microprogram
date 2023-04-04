package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.LoveStart;
import cn.jokeo.lovepig.entity.request.AcceptLoveRequest;
import cn.jokeo.lovepig.entity.request.CreateLoveStarRequest;
import cn.jokeo.lovepig.entity.vo.CreateLoveStartVo;
import cn.jokeo.lovepig.entity.vo.LoveStartVo;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
public interface ILoveStartService extends IService<LoveStart> {

    R<LoveStartVo> queryLoveStartInfo(Long loveId);

    /**
     * 查询当前恋爱空间的主人
     *
     * @param loveId
     * @return
     */
    List<Long> queryHost(Long loveId);

    /**
     * 输入我的id查询我对象的id
     *
     * @param loveId
     * @param myUserId 我的id
     * @return
     */
    Long queryObject(Long loveId, Long myUserId);

    /**
     * 查询是否是主人
     *
     * @param loveId
     * @param userId
     * @return
     */
    boolean isOwner(Long loveId, Long userId);

    /**
     * 更换小窝图片
     *
     * @param wallpaper
     * @param loveId
     * @return
     */
    R changeRoomWallpaper(String wallpaper, Long loveId);

    /**
     * 创建恋爱空间
     *
     * @param request
     * @return
     */
    R<CreateLoveStartVo> createLoveStart(CreateLoveStarRequest request);

    /**
     * 接收爱
     *
     * @param request
     * @return
     */
    R<Boolean> acceptLove(AcceptLoveRequest request);
}
