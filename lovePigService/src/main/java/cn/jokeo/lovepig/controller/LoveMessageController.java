package cn.jokeo.lovepig.controller;


import cn.jokeo.lovepig.entity.request.QueryMessageRequest;
import cn.jokeo.lovepig.entity.request.SaveMessageRequest;
import cn.jokeo.lovepig.entity.vo.LoveMessageVo;
import cn.jokeo.lovepig.security.annotation.PigLogin;
import cn.jokeo.lovepig.security.annotation.PigRoles;
import cn.jokeo.lovepig.service.ILoveMessageService;
import cn.jokeo.lovepig.utils.PageUtil;
import cn.jokeo.lovepig.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author joke
 * @since 2021-07-08
 */
@RestController
@RequestMapping("/message")
public class LoveMessageController {

    @Resource
    private ILoveMessageService messageService;

    @PostMapping("save")
    @PigLogin
    @PigRoles
    @ApiOperation("保存思念信息")
    public R<Boolean> saveMessage(@RequestBody SaveMessageRequest messageRequest) {
        return messageService.saveMessage(messageRequest);
    }

    @GetMapping("page")
    @ApiOperation("查询思念信息")
    public R<PageUtil<LoveMessageVo>> queryMessage(QueryMessageRequest messageRequest) {
        return messageService.queryMessage(messageRequest);
    }
}
