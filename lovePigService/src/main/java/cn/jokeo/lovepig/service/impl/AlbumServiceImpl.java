package cn.jokeo.lovepig.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.jokeo.lovepig.entity.Album;
import cn.jokeo.lovepig.entity.enums.ResourceEntityTypeEnum;
import cn.jokeo.lovepig.entity.request.*;
import cn.jokeo.lovepig.entity.vo.AlbumVo;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.mapper.AlbumMapper;
import cn.jokeo.lovepig.service.IAlbumService;
import cn.jokeo.lovepig.service.ILoveResourcesService;
import cn.jokeo.lovepig.utils.PageWebUtil;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements IAlbumService {
    @Resource
    private ILoveResourcesService resourcesService;

    @Value("${love.defaultAlbumBanner}")
    private String defaultAlbumBanner;

    @Override
    public R<List<AlbumVo>> queryAlbumList(QueryAlbumRequest albumRequest) {
        IPage<Album> page = new Page<>(albumRequest.getCurrPage(), albumRequest.getPageSize());
        LambdaUpdateWrapper<Album> wrapper = Wrappers.lambdaUpdate(Album.class);
        wrapper.eq(Album::getLoveId, albumRequest.getLoveId());
        wrapper.orderByDesc(Album::getLastUploadTime);
        baseMapper.selectPage(page, wrapper);
        List<AlbumVo> albumVos = BeanUtil.copyToList(page.getRecords(), AlbumVo.class);
        return R.ok(albumVos);
    }

    @Override
    public R<PageWebUtil<ResourceVo>> queryAlbumContent(QueryContentDetailRequest detailRequest) {
        QueryResourcesRequest queryResourcesRequest = new QueryResourcesRequest();
        queryResourcesRequest.setLoveId(detailRequest.getLoveId());
        queryResourcesRequest.setEntityId(detailRequest.getAlbumId());
        queryResourcesRequest.setEntityType(ResourceEntityTypeEnum.ALBUM.getCode());
        queryResourcesRequest.setQueryNew(true);
        queryResourcesRequest.setCurrPage(detailRequest.getCurrPage());
        queryResourcesRequest.setPageSize(detailRequest.getPageSize());
        return resourcesService.queryBindResourcesPage(queryResourcesRequest);
    }

    @Override
    public R<Boolean> changeAlbum(ChangeAlbumRequest changeAlbumRequest) {
        Album album = BeanUtil.copyProperties(changeAlbumRequest, Album.class);
        int i = baseMapper.updateById(album);
        return R.result(i);
    }

    @Override
    public R<Boolean> ChangeUploadInfo(ChangeUploadAlbumRequest changeAlbumRequest) {
        //后端提供上传时间字段
        changeAlbumRequest.setLastUploadTime(new Date());
        boolean b = baseMapper.updateUploadInfo(changeAlbumRequest);
        return R.result(b);
    }

    @Override
    public R<Long> createAlbum(CreateAlbumRequest createAlbumRequest) {
        Album album = BeanUtil.copyProperties(createAlbumRequest, Album.class);
        album.setAlbumBanner(this.defaultAlbumBanner);
        album.setLastUploadTime(new Date());
        int insert = baseMapper.insert(album);
        return R.result(insert).setData(album.getAlbumId());
    }

}
