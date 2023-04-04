package cn.jokeo.lovepig.controller;


import cn.jokeo.lovepig.entity.request.QueryResourcesRequest;
import cn.jokeo.lovepig.entity.request.UploadResourcesRequest;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.security.annotation.PigRoles;
import cn.jokeo.lovepig.service.ILoveResourcesService;
import cn.jokeo.lovepig.utils.PageWebUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.TencentOssUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/resources")
@Log4j2
public class LoveResourcesController {

    @Resource
    private ILoveResourcesService loveResourcesService;

    @Resource
    private TencentOssUtil tencentOssUtil;


    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
    @ApiOperation("上传资源")
    @PigRoles
    public R<String> upload(@RequestParam(value = "file") MultipartFile file,@Valid UploadResourcesRequest resourcesRequest) {
        //初始化文件信息
        String fileUrl = tencentOssUtil.uploadFile(file);
        //判断文件类型
        int fileType = tencentOssUtil.handleFileType(file.getOriginalFilename());
        resourcesRequest.setResourceUrl(fileUrl);
        resourcesRequest.setResourceType(fileType);
        resourcesRequest.setResourceSize(Math.toIntExact(file.getSize()));
        log.info("接收到上传文件请求参数: {}", JSON.toJSONString(resourcesRequest));
        return loveResourcesService.saveUploadResource(resourcesRequest);
    }

    @PostMapping(value = "onlyUpload", headers = "content-type=multipart/form-data")
    @ApiOperation("上传资源")
    @PigRoles
    public R<String> onlyUpload(@RequestParam(value = "file") MultipartFile file) {
        String fileUrl = tencentOssUtil.uploadFile(file);
        return R.ok(fileUrl);
    }

    @GetMapping(value = "getResourceList")
    @ApiOperation("查询资源列表")
    public R<PageWebUtil<ResourceVo>> getResourceList(QueryResourcesRequest resourcesRequest) {
        return loveResourcesService.getResourceList(resourcesRequest);
    }
}
