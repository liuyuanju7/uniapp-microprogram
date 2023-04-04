package cn.jokeo.lovepig.mapper;

import cn.jokeo.lovepig.entity.LovePromise;
import cn.jokeo.lovepig.entity.vo.PromiseRemindVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author joke
 * @since 2022-02-06
 */
public interface LovePromiseMapper extends BaseMapper<LovePromise> {


  List<LovePromise> queryPromiseAndRecord();
  List<PromiseRemindVo> queryNeedRemind();

}
