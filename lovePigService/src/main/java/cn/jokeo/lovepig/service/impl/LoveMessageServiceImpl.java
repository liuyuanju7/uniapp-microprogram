package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.LoveMessage;
import cn.jokeo.lovepig.entity.request.QueryMessageRequest;
import cn.jokeo.lovepig.entity.request.SaveMessageRequest;
import cn.jokeo.lovepig.entity.vo.LoveMessageVo;
import cn.jokeo.lovepig.mapper.LoveMessageMapper;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.service.ILoveMessageService;
import cn.jokeo.lovepig.utils.PageUtil;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-07-08
 */
@Service
public class LoveMessageServiceImpl extends ServiceImpl<LoveMessageMapper, LoveMessage> implements ILoveMessageService {

    @Override
    public R<PageUtil<LoveMessageVo>> queryMessage(QueryMessageRequest messageRequest) {
        Page<LoveMessageVo> page = new Page<>(messageRequest.getCurrPage(), messageRequest.getPageSize());
        baseMapper.selectMessageVo(page, messageRequest);
        PageUtil<LoveMessageVo> pageUtil = new PageUtil<>(page);
        return R.ok(pageUtil);
    }

    @Override
    public R<Boolean> saveMessage(SaveMessageRequest messageRequest) {
        LoveMessage message = new LoveMessage();
        BeanUtil.copyProperties(messageRequest, message);
        message.setUserId(UserHolder.getUserId());
        message.setLoveId(UserHolder.getUser().getLoveId());
        //如果是我们的留言直接放行不用审核
        if (message.getMessageType() == 1) {
            message.setState(1);
        }
        int insert = baseMapper.insert(message);
        return R.result(insert);
    }
}
