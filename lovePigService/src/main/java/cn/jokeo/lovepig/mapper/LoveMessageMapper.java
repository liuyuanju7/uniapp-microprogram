package cn.jokeo.lovepig.mapper;

import cn.jokeo.lovepig.entity.LoveMessage;
import cn.jokeo.lovepig.entity.request.QueryMessageRequest;
import cn.jokeo.lovepig.entity.vo.LoveMessageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author joke
 * @since 2021-07-08
 */
public interface LoveMessageMapper extends BaseMapper<LoveMessage> {
    /**
     * 查询留言列表
     *
     * @param page
     * @param messageRequest
     * @return
     */
    IPage<LoveMessageVo> selectMessageVo(Page<?> page, QueryMessageRequest messageRequest);
}
