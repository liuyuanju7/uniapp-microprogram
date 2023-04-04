package cn.jokeo.lovepig.mapper;

import cn.jokeo.lovepig.entity.LoveStory;
import cn.jokeo.lovepig.entity.model.SelectStoryModel;
import cn.jokeo.lovepig.entity.vo.StoryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
public interface LoveStoryMapper extends BaseMapper<LoveStory> {
    /**
     * 查询故事列表
     *
     * @param model
     * @return
     */
    List<StoryVo> selectStoryVo(SelectStoryModel model);

    Integer selectCount(SelectStoryModel model);

    /**
     * 查询故事详细信息
     *
     * @param storyId
     * @return
     */
    StoryVo selectStoryVoById(Long storyId);
}
