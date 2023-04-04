package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.LoveStory;
import cn.jokeo.lovepig.entity.request.QueryStoryRequest;
import cn.jokeo.lovepig.entity.request.SaveStoryRequest;
import cn.jokeo.lovepig.entity.vo.StoryVo;
import cn.jokeo.lovepig.utils.PageUtil;
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
public interface ILoveStoryService extends IService<LoveStory> {
    /**
     * 查询故事列表
     *
     * @param storyRequest
     * @return
     */
    R<PageUtil<StoryVo>> queryPage(QueryStoryRequest storyRequest);

    /**
     * 查询故事详细信息
     *
     * @param storyId
     * @return
     */
    R<StoryVo> queryStoryDetail(Long storyId);

    /**
     * 保存故事信息
     *
     * @param storyRequest
     * @return
     */
    R<Long> saveStory(SaveStoryRequest storyRequest);

    /**
     * 储存故事以及承诺完成记录
     *
     * @param storyRequest
     * @return
     */
    R<Long> saveStoryAndPromise(SaveStoryRequest storyRequest);

}
