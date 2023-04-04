package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.jokeo.lovepig.entity.LovePromise;
import cn.jokeo.lovepig.entity.LovePromiseRecord;
import cn.jokeo.lovepig.entity.enums.PromiseStateEnum;
import cn.jokeo.lovepig.entity.enums.PromiseTypeEnum;
import cn.jokeo.lovepig.entity.request.QueryPromiseRequest;
import cn.jokeo.lovepig.entity.request.SavePromiseRequest;
import cn.jokeo.lovepig.entity.vo.PromiseListVo;
import cn.jokeo.lovepig.entity.vo.PromisePageVo;
import cn.jokeo.lovepig.entity.vo.PromiseRemindVo;
import cn.jokeo.lovepig.entity.vo.UserBaseInfoVo;
import cn.jokeo.lovepig.mapper.LovePromiseMapper;
import cn.jokeo.lovepig.service.ILovePromiseRecordService;
import cn.jokeo.lovepig.service.ILovePromiseService;
import cn.jokeo.lovepig.utils.PromiseDateUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.cache.LoveStartCache;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-07-14
 */
@Service
@Slf4j
public class LovePromiseServiceImpl extends ServiceImpl<LovePromiseMapper, LovePromise> implements ILovePromiseService {

    @Resource
    private LoveStartCache loveStartCache;

    @Resource
    private ILovePromiseRecordService lovePromiseRecordService;

    @Override
    public R<List<PromiseListVo>> queryPromiseList(QueryPromiseRequest promiseRequest) {
        Page<LovePromise> page = new Page<>(promiseRequest.getCurrPage(), promiseRequest.getPageSize());
        LambdaQueryWrapper<LovePromise> wrapper = Wrappers.lambdaQuery(LovePromise.class);
        wrapper.eq(LovePromise::getLoveId, promiseRequest.getLoveId());
        wrapper.orderByAsc(LovePromise::getTargetTime);
        if (promiseRequest.getPromiseType() != null) {
            wrapper.eq(LovePromise::getPromiseType, promiseRequest.getPromiseType());
        }
        wrapper.eq(LovePromise::getPromiseState, promiseRequest.getPromiseState());
        baseMapper.selectPage(page, wrapper);

        List<LovePromise> records = page.getRecords();
        List<PromiseListVo> promiseListVos = new ArrayList<>();
        records.forEach(p -> promiseListVos.add(fillPromise(p)));
        //根据距离天数排序
        promiseListVos.sort(Comparator.comparing(PromiseListVo::getDaysApart));
        return R.ok(promiseListVos);
    }

    @Override
    public R<PromisePageVo> promisePage(QueryPromiseRequest promiseRequest) {
        PromisePageVo promisePage = new PromisePageVo();
        promiseRequest.setCurrPage(1);
        promiseRequest.setPageSize(10);
        R<List<PromiseListVo>> listR = queryPromiseList(promiseRequest);
        Long count = lovePromiseRecordService.lambdaQuery().eq(LovePromiseRecord::getLoveId, promiseRequest.getLoveId()).count();
        promisePage.setPromiseList(listR.getData());
        promisePage.setComplete(count);
        promisePage.setUndone(listR.getData() == null ? 0 : listR.getData().size());
        return R.ok(promisePage);
    }

    @Override
    public R<PromiseListVo> promiseDetail(Long promiseId) {
        LovePromise lovePromise = baseMapper.selectById(promiseId);
        PromiseListVo promiseListVo = fillPromise(lovePromise);
        return R.ok(promiseListVo);
    }

    @Override
    public R<Boolean> savePromise(SavePromiseRequest savePromiseRequest) {
        LovePromise lovePromise = BeanUtil.copyProperties(savePromiseRequest, LovePromise.class);
        //添加时计算承诺/纪念日的目标时间
        PromiseDateUtil.calculateTargetTime(lovePromise);
        boolean save = save(lovePromise);
        return R.result(save);
    }

    @Override
    public Boolean queryAndUpdatePromiseState() {
        /*
        一.承诺状态
        1.查询所有状态为1
        2.承诺类型
        3.查询承诺到期时间小于今天的数据(说明承诺已经过去)
            计算过程
           1,一次性承诺查看到承诺日期是否有完成记录,没有就算是未完成
           2.循环承诺,查询当前承诺周期是否有完成记录

         */
        //先处理一次性的承诺.
        LambdaQueryWrapper<LovePromise> wrapper = Wrappers.lambdaQuery(LovePromise.class);
        wrapper.eq(LovePromise::getPromiseType, PromiseTypeEnum.PROMISE.getCode())
                .eq(LovePromise::getPromiseState, 1)
                .eq(LovePromise::getPromiseRepeat, "1O")
                .le(LovePromise::getTargetTime, DateUtil.today());
        wrapper.select(LovePromise::getPromiseId);
        List<LovePromise> lovePromises = baseMapper.selectList(wrapper);
        log.info("{} 未完成的一次性承诺id有: {}", DateUtil.now(), lovePromises.toString());
        lovePromises.forEach(lp -> lp.setPromiseState(PromiseStateEnum.UNFINISHED.getCode()));
        //非一次性的承诺
//        LambdaQueryWrapper<LovePromise> wrapper1 = Wrappers.lambdaQuery(LovePromise.class);
//        wrapper1.eq(LovePromise::getPromiseType, PromiseTypeEnum.PROMISE)
//                .eq(LovePromise::getPromiseState, 1)
//                .ne(LovePromise::getPromiseRepeat, "1O")
//                .le(LovePromise::getTargetTime, DateUtil.today());

        return updateBatchById(lovePromises);
    }

    @Override
    public Boolean queryAndUpdatePromiseTargetTime() {
        LambdaQueryWrapper<LovePromise> wrapper = Wrappers.lambdaQuery(LovePromise.class);
        wrapper.eq(LovePromise::getPromiseState, 1)
                .ne(LovePromise::getPromiseRepeat, "1O")
                .ne(LovePromise::getPromiseType, PromiseTypeEnum.UNLIMITED_TIME.getCode())
                .le(LovePromise::getTargetTime, DateUtil.today());
        wrapper.select(LovePromise::getPromiseId, LovePromise::getPromiseTime, LovePromise::getPromiseRepeat);
        List<LovePromise> lovePromises = baseMapper.selectList(wrapper);
        lovePromises.forEach(PromiseDateUtil::calculateTargetTime);
        log.info("{},更新承诺的目标时间: {}", DateUtil.now(), JSON.toJSONString(lovePromises));
        return updateBatchById(lovePromises);
    }

    @Override
    public List<PromiseRemindVo> queryNeedRemind() {
//        LambdaQueryWrapper<LovePromise> wrapper = Wrappers.lambdaQuery(LovePromise.class);
//        wrapper.eq(LovePromise::getPromiseState, 1)
//                .ne(LovePromise::getPromiseRemind, "-1")
//                .ne(LovePromise::getPromiseType, PromiseTypeEnum.UNLIMITED_TIME.getCode())
//                .apply("date_add(current_date,interval promise_remind day)=target_time");
//        List<LovePromise> lovePromises = baseMapper.selectList(wrapper);
//        List<PromiseListVo> promiseListVos = new ArrayList<>();
//        lovePromises.forEach(p -> promiseListVos.add(fillPromise(p)));
        return baseMapper.queryNeedRemind();
    }

    private PromiseListVo fillPromise(LovePromise p) {
        PromiseListVo promiseListVo = BeanUtil.copyProperties(p, PromiseListVo.class);
        if (p.getPromiseType() == PromiseTypeEnum.UNLIMITED_TIME.getCode()) {
            //不限时承诺显示过去多少天
            promiseListVo.setShowType((byte) 2);
            promiseListVo.setPastDays(PromiseDateUtil.promiseLastDay(p.getPromiseTime()));
        } else {
            PromiseDateUtil.promiseDay(promiseListVo);
        }
        UserBaseInfoVo userBaseInfoVo = loveStartCache.getUser(p.getLoveId(), p.getUserId());
        promiseListVo.setUser(userBaseInfoVo);
        return promiseListVo;
    }
}
