package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.LoveMessage;
import cn.jokeo.lovepig.entity.request.QueryMessageRequest;
import cn.jokeo.lovepig.entity.request.SaveMessageRequest;
import cn.jokeo.lovepig.entity.vo.LoveMessageVo;
import cn.jokeo.lovepig.utils.PageUtil;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2021-07-08
 */
public interface ILoveMessageService extends IService<LoveMessage> {

    /**
     * 储存留言
     *
     * @param messageRequest
     * @return
     */
    R<Boolean> saveMessage(SaveMessageRequest messageRequest);

    /**
     * 查询留言内容
     *
     * @param messageRequest
     * @return
     */
    R<PageUtil<LoveMessageVo>> queryMessage(QueryMessageRequest messageRequest);
}
