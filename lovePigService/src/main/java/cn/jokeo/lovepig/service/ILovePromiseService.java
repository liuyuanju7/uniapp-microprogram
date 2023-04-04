package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.LovePromise;
import cn.jokeo.lovepig.entity.request.QueryPromiseRequest;
import cn.jokeo.lovepig.entity.request.SavePromiseRequest;
import cn.jokeo.lovepig.entity.vo.PromiseListVo;
import cn.jokeo.lovepig.entity.vo.PromisePageVo;
import cn.jokeo.lovepig.entity.vo.PromiseRemindVo;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2021-07-14
 */
public interface ILovePromiseService extends IService<LovePromise> {
    /**
     * 查询承诺列表
     *
     * @param promiseRequest
     * @return
     */
    R<List<PromiseListVo>> queryPromiseList(QueryPromiseRequest promiseRequest);

    /**
     * 承诺页面
     *
     * @param promiseRequest
     * @return
     */
    R<PromisePageVo> promisePage(QueryPromiseRequest promiseRequest);

    /**
     * 查询承诺信息(包括承诺的完成结果)
     *
     * @param promiseId
     * @return
     */
    R<PromiseListVo> promiseDetail(Long promiseId);

    /**
     * 新增承诺
     *
     * @param savePromiseRequest
     * @return
     */
    R<Boolean> savePromise(SavePromiseRequest savePromiseRequest);

    /**
     * 查询并更新承诺到期时间
     * 以及承诺失效
     *
     * @return
     */
    Boolean queryAndUpdatePromiseState();

    /**
     * 查询并且更新承诺以及纪念日的目标时间  执行顺序地于
     *
     * @return
     */
    Boolean queryAndUpdatePromiseTargetTime();

    /**
     * 查询需要提醒的数据
     *
     * @return
     */
    List<PromiseRemindVo> queryNeedRemind();


}
