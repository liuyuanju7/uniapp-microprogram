package cn.jokeo.lovepig.service;

import cn.jokeo.lovepig.entity.LoveResources;
import cn.jokeo.lovepig.entity.request.QueryResourcesRequest;
import cn.jokeo.lovepig.entity.request.UploadResourcesRequest;
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
 * @since 2021-06-20
 */
public interface ILoveResourcesService extends IService<LoveResources> {
    /**
     * 查询绑定资源的信息(只返回url够用即可)
     *
     * @param resourceRequest
     * @return
     */
    List<ResourceVo> queryBindResources(QueryResourcesRequest resourceRequest);

    R<PageWebUtil<ResourceVo>> queryBindResourcesPage(QueryResourcesRequest resourceRequest);

    /**
     * 查询绑定资源的具体信息
     *
     * @param resourceRequest
     * @return
     */
    List<LoveResources> queryBindResourcesDetail(QueryResourcesRequest resourceRequest);

    /**
     * 保存上传的资源信息
     *
     * @param resourcesRequest
     * @return
     */
    R<String> saveUploadResource( UploadResourcesRequest resourcesRequest);

    /**
     * 分页查询资源
     *
     * @param resourcesRequest
     * @return
     */
    R<PageWebUtil<ResourceVo>> getResourceList(QueryResourcesRequest resourcesRequest);
}
