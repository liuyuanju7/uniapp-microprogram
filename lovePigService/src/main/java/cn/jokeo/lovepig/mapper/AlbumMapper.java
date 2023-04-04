package cn.jokeo.lovepig.mapper;

import cn.jokeo.lovepig.entity.Album;
import cn.jokeo.lovepig.entity.request.ChangeUploadAlbumRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
public interface AlbumMapper extends BaseMapper<Album> {
    /**
     * 上传图片更新相册上传信息
     *
     * @param request
     * @return
     */
    boolean updateUploadInfo(ChangeUploadAlbumRequest request);
}
