package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.Album;
import cn.jokeo.lovepig.entity.request.*;
import cn.jokeo.lovepig.entity.vo.AlbumVo;
import cn.jokeo.lovepig.entity.vo.ResourceVo;
import cn.jokeo.lovepig.utils.PageWebUtil;
import cn.jokeo.lovepig.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
public interface IAlbumService extends IService<Album> {

    /**
     * 查询相册列表
     *
     * @param albumRequest
     * @return
     */
    R<List<AlbumVo>> queryAlbumList(QueryAlbumRequest albumRequest);

    /**
     * 查询相册内容
     *
     * @param detailRequest
     * @return
     */
    R<PageWebUtil<ResourceVo>> queryAlbumContent(QueryContentDetailRequest detailRequest);

    /**
     * 创建相册
     *
     * @param createAlbumRequest
     * @return
     */
    R<Long> createAlbum(CreateAlbumRequest createAlbumRequest);

    /**
     * 修改相册基本信息
     *
     * @param changeAlbumRequest@return
     */
    R<Boolean> changeAlbum(ChangeAlbumRequest changeAlbumRequest);

    /**
     * 上传图片后更新相册的信息
     *
     * @param changeAlbumRequest
     * @return
     */
    R<Boolean> ChangeUploadInfo(ChangeUploadAlbumRequest changeAlbumRequest);

}
