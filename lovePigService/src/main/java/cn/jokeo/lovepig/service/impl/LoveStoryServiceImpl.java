package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.common.exception.LoveException;
import cn.jokeo.lovepig.entity.LovePromiseRecord;
import cn.jokeo.lovepig.entity.LoveStory;
import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.entity.model.SelectStoryModel;
import cn.jokeo.lovepig.entity.request.QueryStoryRequest;
import cn.jokeo.lovepig.entity.request.SaveStoryRequest;
import cn.jokeo.lovepig.entity.vo.StoryVo;
import cn.jokeo.lovepig.mapper.LoveStoryMapper;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.service.ILovePromiseRecordService;
import cn.jokeo.lovepig.service.ILoveStoryService;
import cn.jokeo.lovepig.utils.PageUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.TencentOssUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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
@Slf4j
public class LoveStoryServiceImpl extends ServiceImpl<LoveStoryMapper, LoveStory> implements ILoveStoryService {

    @Resource
    private TencentOssUtil tencentOssUtil;
    @Resource
    private ILovePromiseRecordService promiseRecordService;

    @Override
    public R<PageUtil<StoryVo>> queryPage(QueryStoryRequest storyRequest) {
        SelectStoryModel selectStoryModel = new SelectStoryModel();
        BeanUtil.copyProperties(storyRequest, selectStoryModel);
        selectStoryModel.setOffset((storyRequest.getCurrPage() - 1) * storyRequest.getPageSize());


        List<StoryVo> storyVos = baseMapper.selectStoryVo(selectStoryModel);
        Integer count = baseMapper.selectCount(selectStoryModel);
        PageUtil<StoryVo> pageUtil = new PageUtil<>(count, storyRequest.getPageSize(), storyRequest.getCurrPage(), storyVos);
        return R.ok(pageUtil);
    }


    @Override
    public R<StoryVo> queryStoryDetail(Long storyId) {
        if (storyId == null) {
            return R.fail("请提供故事id！");
        }
        StoryVo storyVo = baseMapper.selectStoryVoById(storyId);
        return R.ok(storyVo);
    }


    @Override
    public R<Long> saveStory(SaveStoryRequest storyRequest) {
        //空值判断
        checkParams(storyRequest);
        LoveStory loveStory = new LoveStory();
        BeanUtil.copyProperties(storyRequest, loveStory);
        loveStory.setUserId(UserHolder.getUserId());
        //处理恋爱信息
        processLoveStoryInfo(loveStory);
        log.info("落库的故事信息:" + loveStory);
        int insert = baseMapper.insert(loveStory);
        return R.result(insert).setData(loveStory.getStoryId());
    }

    @Override
    @Transactional
    public R<Long> saveStoryAndPromise(SaveStoryRequest storyRequest) {
        //空值判断
        checkParams(storyRequest);
        LoveStory loveStory = BeanUtil.copyProperties(storyRequest, LoveStory.class);
        loveStory.setUserId(UserHolder.getUserId());
        //处理恋爱信息
        processLoveStoryInfo(loveStory);
        log.info("落库的故事信息:" + loveStory);
        boolean save = save(loveStory);
        if (!save) {
            throw new LoveException(LoveErrorEnum.SAVE_STORY_ERROR);
        }
        //储存承诺完成记录
        LovePromiseRecord lovePromiseRecord = BeanUtil.copyProperties(storyRequest.getPromise(), LovePromiseRecord.class);
        lovePromiseRecord.setStoryId(loveStory.getStoryId());
        lovePromiseRecord.setLoveId(storyRequest.getLoveId());
        lovePromiseRecord.setFinishUserId(UserHolder.getUserId());
        lovePromiseRecord.setFinishTime(new Date());
        boolean savePromiseRecord = promiseRecordService.save(lovePromiseRecord);
        R<Long> result = R.result(save && savePromiseRecord);
        result.setData(loveStory.getStoryId());
        return result;
    }

    /**
     * 检查参数
     *
     * @param storyRequest
     * @return
     */
    private void checkParams(SaveStoryRequest storyRequest) {
        if (storyRequest.getStoryType() == 2 || storyRequest.getStoryType() == 3) {
            Assert.isTrue(storyRequest.getTitle().length() > 5, "文章必须提供标题且必须超过5个字！");
            Assert.hasText(storyRequest.getBanner(), "文章必须提供头图！");
        }
    }

    /**
     * 处理恋爱信息
     *
     * @param loveStory
     */
    private void processLoveStoryInfo(LoveStory loveStory) {
        //处理摘要
        String content = loveStory.getContent();
        //todo 添加xss过滤器

        //todo 剔除html标签
        //截取100个子，如果长度不超过100个子，截取全部
        //todo 这里截取的内容可能包含特殊符号尽量截取成完整的内容
        loveStory.setSummary(content.substring(0, Math.min(content.length(), 100)));

        //根据位置，查询天气

        //如果没有指定故事的发生时间，默认就是今天啦
        if (loveStory.getStoryTime() == null) {
            loveStory.setStoryTime(new Date());
        }
        loveStory.setPublishTime(new Date());
    }
}
