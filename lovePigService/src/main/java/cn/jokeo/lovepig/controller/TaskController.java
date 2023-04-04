package cn.jokeo.lovepig.controller;

import cn.jokeo.lovepig.autotask.PromiseTask;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 手动触发定时任务
 *
 * @author joke
 * @date 2021/8/17 10:36
 */
@Api(tags = "手动触发定时任务")
@RestController
@RequestMapping("/task")
@Log4j2
public class TaskController {
    @Resource
    private PromiseTask promiseTask;

    @GetMapping("checkPromiseAging")
    @ApiOperation("检查并更新承诺状态和时间")
    public R queryPage() {
        promiseTask.checkPromiseAging();
        return R.ok();
    }

}
