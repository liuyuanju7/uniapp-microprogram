package cn.jokeo.lovepig.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.LovePromise;
import cn.jokeo.lovepig.entity.request.QueryPromiseRequest;
import cn.jokeo.lovepig.entity.request.SavePromiseRequest;
import cn.jokeo.lovepig.entity.vo.PromiseListVo;
import cn.jokeo.lovepig.entity.vo.PromisePageVo;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.security.annotation.PigLogin;
import cn.jokeo.lovepig.security.annotation.PigRoles;
import cn.jokeo.lovepig.service.ILovePromiseService;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2021-07-14
 */
@Api(tags = "承诺或纪念日")
@RestController
@RequestMapping("/promise")
public class LovePromiseController {

    @Autowired
    private ILovePromiseService lovePromiseService;

    @GetMapping("promisePage")
    @ApiOperation("查询承诺列表页面")
    public R<PromisePageVo> promisePage(QueryPromiseRequest promiseRequest) {
        return lovePromiseService.promisePage(promiseRequest);
    }

    @GetMapping("promiseList")
    @ApiOperation("查询承诺列表列表")
    public R<List<PromiseListVo>> promiseList(QueryPromiseRequest promiseRequest) {
        return lovePromiseService.queryPromiseList(promiseRequest);
    }

    @GetMapping("promiseDetail")
    @ApiOperation("查询承诺详细信息")
    public R<PromiseListVo> queryPromiseDetail(QueryPromiseRequest promiseRequest) {
        return lovePromiseService.promiseDetail(promiseRequest.getPromiseId());
    }

    @PostMapping("savePromise")
    @ApiOperation("添加承诺")
    @PigLogin
    @PigRoles
    public R<Boolean> savePromise(@RequestBody SavePromiseRequest promiseRequest) {
        promiseRequest.setUserId(UserHolder.getUserId());
        return lovePromiseService.savePromise(promiseRequest);
    }

    @PostMapping("editPromise")
    @ApiOperation("编辑承诺")
    @PigLogin
    @PigRoles
    public R<Boolean> editPromise(@RequestBody SavePromiseRequest promiseRequest) {
        LovePromise lovePromise = BeanUtil.copyProperties(promiseRequest, LovePromise.class);
        boolean editR = lovePromiseService.updateById(lovePromise);
        return R.result(editR);
    }
}
