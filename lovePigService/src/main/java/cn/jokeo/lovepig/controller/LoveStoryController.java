package cn.jokeo.lovepig.controller;


import cn.hutool.core.lang.Assert;
import cn.jokeo.lovepig.config.LoveConfig;
import cn.jokeo.lovepig.entity.request.QueryStoryRequest;
import cn.jokeo.lovepig.entity.request.SaveStoryRequest;
import cn.jokeo.lovepig.entity.vo.StoryVo;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.annotation.PigLogin;
import cn.jokeo.lovepig.security.annotation.PigRoles;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.service.ILoveStoryService;
import cn.jokeo.lovepig.utils.PageUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.TencentOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Api(tags = "故事")
@RestController
@RequestMapping("/story")
@Slf4j
public class LoveStoryController {

    @Resource
    private ILoveStoryService storyService;

    @Resource
    private TencentOssUtil tencentOssUtil;

    @Resource
    private LoveConfig loveConfig;
  @Resource
    private ILoveStartService loveStartService;

    @GetMapping("page")
    @ApiOperation("查询故事列表")
    public R<PageUtil<StoryVo>> queryPage(QueryStoryRequest storyRequest) {
        if (storyRequest.getOriginator() == 1) {
            storyRequest.setOriginator(UserHolder.getUserId());
        } else if (storyRequest.getOriginator() == 2) {
            System.out.println(UserHolder.getUserId());
            Long objectId = loveStartService.queryObject(storyRequest.getLoveId(), UserHolder.getUserId());
            storyRequest.setOriginator(objectId);
        } else {
            storyRequest.setOriginator(null);
        }
        return storyService.queryPage(storyRequest);
    }

    @GetMapping("/{storyId}")
    @ApiOperation("查询故事详细信息")
    public R<StoryVo> queryStory(@PathVariable Long storyId) {
        return storyService.queryStoryDetail(storyId);
    }

    /**
     * 发布动态/文章功能实现
     * <p>
     * （做成后台任务-异步出发，不影响当前页面）
     * 前端
     * 发起同步请求先保存文章信息，之后保存图片信息
     * 发布之前所有的信息保存到本地缓存中，
     * 点发布直接跳到文章页面（此时调用的是本地缓存）-后台正在默默上传信息
     * <p>
     * 后端
     * 1.首先保存文件信息，
     * 2.依次上传图片并提供返回的文章id，保存到资源库中
     *
     * @param storyRequest
     * @return
     */
    @PostMapping(value = "save")
    @ApiOperation("保存故事")
    @PigLogin
    @PigRoles
    public R<Long> saveStory(@RequestBody SaveStoryRequest storyRequest) {
        return storyService.saveStory(storyRequest);
    }

    @PostMapping(value = "saveAndPromise")
    @ApiOperation("保存故事和承诺完成记录")
    @PigLogin
    @PigRoles
    public R<Long> saveStoryAndPromise(@RequestBody SaveStoryRequest storyRequest) {
        return storyService.saveStoryAndPromise(storyRequest);
    }

    @PostMapping(value = "uploadBanner", headers = "content-type=multipart/form-data")
    @ApiOperation("上传故事的头图")
    public R<String> uploadBanner(@RequestParam("banner") MultipartFile banner) {
        Assert.isTrue(!banner.isEmpty(), "上传的文件不能为空!");
        String filePath = tencentOssUtil.genFilePath(banner.getOriginalFilename());
        //保存文件到oss
        try {
            tencentOssUtil.upload(banner.getInputStream(), banner.getSize(), filePath);
            log.info("向腾讯云cos上传文件,路径为:" + loveConfig.getStaticUrl() + filePath);
        } catch (Exception e) {
            log.error("上传文件异常:" + e.getMessage());
            return R.fail("上传文件出现异常!");
        }
        return R.ok(loveConfig.getStaticUrl() + filePath);
    }
}
