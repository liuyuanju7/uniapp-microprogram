package cn.jokeo.lovepig.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.Album;
import cn.jokeo.lovepig.entity.request.*;
import cn.jokeo.lovepig.entity.vo.AlbumVo;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.service.IAlbumService;
import cn.jokeo.lovepig.service.ILoveResourcesService;
import cn.jokeo.lovepig.utils.PageWebUtil;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

    @Resource
    private IAlbumService albumService;
    @Resource
    private ILoveResourcesService loveResourcesService;

    @GetMapping("albumList")
    @ApiOperation("查询相册列表")
    public R<List<AlbumVo>> queryAlbumList(QueryAlbumRequest albumRequest) {
        return albumService.queryAlbumList(albumRequest);
    }

    @GetMapping("albumInfo")
    @ApiOperation("查询相册列表")
    public R<AlbumVo> queryAlbumInfo(@RequestParam Long albumId) {
        Album album = albumService.getById(albumId);
        AlbumVo albumVo = BeanUtil.copyProperties(album, AlbumVo.class);
        return R.ok(albumVo);
    }

    @GetMapping("albumContent")
    @ApiOperation("查询相册内容")
    public R<PageWebUtil<ResourceVo>> queryAlbumContent(@Valid QueryContentDetailRequest detailRequest) {
        return albumService.queryAlbumContent(detailRequest);
    }

    @PostMapping("createAlbum")
    @ApiOperation("创建相册")
    public R<Long> createAlbum(@Valid @RequestBody CreateAlbumRequest createAlbumRequest) {
        createAlbumRequest.setUserId(UserHolder.getUserId());
        return albumService.createAlbum(createAlbumRequest);
    }

    @PostMapping("changeAlbum")
    @ApiOperation("修改相册信息")
    public R<Boolean> changeAlbum(@Valid @RequestBody ChangeAlbumRequest changeAlbumRequest) {
        return albumService.changeAlbum(changeAlbumRequest);
    }

    @PostMapping("ChangeUploadInfo")
    @ApiOperation("上传照片完成更新相册信息")
    public R<Boolean> ChangeUploadInfo(@RequestBody ChangeUploadAlbumRequest changeAlbumRequest) {
        return albumService.ChangeUploadInfo(changeAlbumRequest);
    }
}
