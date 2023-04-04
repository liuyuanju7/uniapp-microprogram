package cn.jokeo.lovepig.controller;


import cn.jokeo.lovepig.entity.request.AcceptLoveRequest;
import cn.jokeo.lovepig.entity.request.CreateLoveStarRequest;
import cn.jokeo.lovepig.entity.vo.CreateLoveStartVo;
import cn.jokeo.lovepig.entity.vo.LoveStartVo;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "恋爱开始")
@RestController
@RequestMapping("/loveStart")
public class LoveStartController {
    @Resource
    private ILoveStartService loveStartService;

    @GetMapping("")
    @ApiOperation("查询恋爱空间信息")
    public R<LoveStartVo> queryLoveStart(Long loveId) {
        return loveStartService.queryLoveStartInfo(loveId);
    }

    @PostMapping("/changeRoomWallpaper")
    @ApiOperation("更换小窝图片")
    public R changeRoomWallpaper(String wallpaper, Long loveId) {
        return loveStartService.changeRoomWallpaper(wallpaper, loveId);
    }

    @PostMapping("/createLoveStart")
    @ApiOperation("创建小窝")
    public R<CreateLoveStartVo> createLoveStart(@RequestBody @Valid CreateLoveStarRequest request) {
        request.setInviteUser(UserHolder.getUserId());
        return loveStartService.createLoveStart(request);
    }

    @PostMapping("/acceptLove")
    @ApiOperation("接收邀请")
    public R<Boolean> acceptLove(@RequestBody @Valid AcceptLoveRequest request) {
        request.setAcceptUser(UserHolder.getUserId());
        return loveStartService.acceptLove(request);
    }


}
