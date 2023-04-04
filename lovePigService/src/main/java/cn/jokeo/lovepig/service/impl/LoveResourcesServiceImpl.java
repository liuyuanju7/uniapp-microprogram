package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.LoveResources;
import cn.jokeo.lovepig.entity.ResourceRelate;
import cn.jokeo.lovepig.entity.enums.ResourceEntityTypeEnum;
import cn.jokeo.lovepig.entity.request.QueryResourcesRequest;
import cn.jokeo.lovepig.entity.request.UploadResourcesRequest;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.mapper.LoveResourcesMapper;
import cn.jokeo.lovepig.mapper.ResourceRelateMapper;
import cn.jokeo.lovepig.security.UserHolder;
import cn.jokeo.lovepig.service.ILoveResourcesService;
import cn.jokeo.lovepig.service.ILoveStartService;
import cn.jokeo.lovepig.utils.PageWebUtil;
import cn.jokeo.lovepig.utils.R;
import cn.jokeo.lovepig.utils.TencentOssUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2021-06-20
 */
@Service
@Slf4j
public class LoveResourcesServiceImpl extends ServiceImpl<LoveResourcesMapper, LoveResources> implements ILoveResourcesService {


    @Resource
    private TencentOssUtil tencentOssUtil;

    @Resource
    private ILoveStartService loveStartService;

    @Resource
    private ResourceRelateMapper resourceRelateMapper;

    @Override
    public List<ResourceVo> queryBindResources(QueryResourcesRequest resourceRequest) {
        LambdaQueryWrapper<ResourceRelate> wrapper = Wrappers.lambdaQuery(ResourceRelate.class);
        wrapper.eq(ResourceRelate::getEntityId, resourceRequest.getEntityId())
                .eq(ResourceRelate::getEntityType, resourceRequest.getEntityType());
        List<ResourceRelate> resourceRelates = resourceRelateMapper.selectList(wrapper);
        List<ResourceVo> resourceVos = new ArrayList<>();
        resourceRelates.forEach(resourceRelate -> {
            resourceVos.add(new ResourceVo(resourceRelate.getResourceId(), resourceRelate.getResourceUrl()));
        });
        return resourceVos;
    }

    @Override
    public R<PageWebUtil<ResourceVo>> queryBindResourcesPage(QueryResourcesRequest resourceRequest) {
        LambdaQueryWrapper<ResourceRelate> wrapper = Wrappers.lambdaQuery(ResourceRelate.class);
        wrapper.eq(ResourceRelate::getEntityId, resourceRequest.getEntityId())
                .eq(ResourceRelate::getEntityType, resourceRequest.getEntityType());
        if (resourceRequest.getQueryNew()) {
            wrapper.orderByDesc(ResourceRelate::getRelateId);
        }
        Page<ResourceRelate> page = new Page<>(resourceRequest.getCurrPage(), resourceRequest.getPageSize());
        resourceRelateMapper.selectPage(page, wrapper);
        List<ResourceVo> resourceVos = BeanUtil.copyToList(page.getRecords(), ResourceVo.class);
        PageWebUtil<ResourceVo> resourceVoPageWebUtil = new PageWebUtil<>(page.getTotal(), resourceVos);
        return R.ok(resourceVoPageWebUtil);
    }

    @Override
    public List<LoveResources> queryBindResourcesDetail(QueryResourcesRequest resourceRequest) {
        return null;
    }

    @Override
    public R<PageWebUtil<ResourceVo>> getResourceList(QueryResourcesRequest resourcesRequest) {
        LambdaQueryWrapper<LoveResources> resourceWrapper = Wrappers.lambdaQuery(LoveResources.class);
        resourceWrapper.eq(LoveResources::getLoveId, resourcesRequest.getLoveId())
                .eq(LoveResources::getResourceType, resourcesRequest.getResourceType())
                .eq(LoveResources::getState, 1);
        if (resourcesRequest.getQueryNew()) {
            resourceWrapper.orderByDesc(LoveResources::getCreateTime);
        }
        Page<LoveResources> page = new Page<>(resourcesRequest.getCurrPage(), resourcesRequest.getPageSize());
        baseMapper.selectPage(page, resourceWrapper);
        List<ResourceVo> resourceVos = BeanUtil.copyToList(page.getRecords(), ResourceVo.class);
        PageWebUtil<ResourceVo> pageWebUtil = new PageWebUtil<>(page.getTotal(), resourceVos);
        return R.ok(pageWebUtil);
    }

    @Override
    @Transactional
    public R<String> saveUploadResource(UploadResourcesRequest resourcesRequest) {
        LoveResources resources = new LoveResources();
        //上传文件
        BeanUtil.copyProperties(resourcesRequest, resources);
        resources.setUserId(UserHolder.getUserId());
        int resourceInsert = baseMapper.insert(resources);
        log.info("[saveUploadResource]保存上传文件请求信息: {},\n操作结果: {}", JSON.toJSONString(resourcesRequest), resourceInsert);

        //图片和内容关联
        ResourceRelate resourceRelate = new ResourceRelate();
        resourceRelate.setResourceId(resources.getResourceId());
        resourceRelate.setResourceUrl(resources.getResourceUrl());
        int resourceRelateRes;
        //如果是修改背景就更新
        if (resourcesRequest.getEntityType() == ResourceEntityTypeEnum.LOVE_START.getCode()) {
            LambdaUpdateWrapper<ResourceRelate> wrapper = Wrappers.lambdaUpdate(ResourceRelate.class);
            wrapper.eq(ResourceRelate::getEntityId, resourcesRequest.getLoveId());
            wrapper.eq(ResourceRelate::getEntityType, ResourceEntityTypeEnum.LOVE_START.getCode());
            resourceRelateRes = resourceRelateMapper.update(resourceRelate, wrapper);
        } else {
            resourceRelate.setEntityId(resourcesRequest.getEntityId());
            resourceRelate.setEntityType(resourcesRequest.getEntityType());
            resourceRelateRes = resourceRelateMapper.insert(resourceRelate);
            //如果是发布文章的照片也同样关联到故事相册
//            if (resourcesRequest.getEntityType() == ResourceEntityTypeEnum.STORY.getCode()) {
//                resourceRelate.setEntityType(ResourceEntityTypeEnum.ALBUM.getCode());
//                resourceRelateMapper.insert(resourceRelate);
//            }
        }
        return R.result(resourceInsert == 1 && resourceRelateRes == 1).setData(resources.getResourceUrl());

    }
}
