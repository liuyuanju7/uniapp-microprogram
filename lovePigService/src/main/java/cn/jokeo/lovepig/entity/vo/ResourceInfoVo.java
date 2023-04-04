package cn.jokeo.lovepig.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回详细的资源信息
 *
 * @author joke
 * @version 1.0
 * @date 2021/7/13 21:06
 */
@Data
public class ResourceInfoVo {
    private Integer id;

    @ApiModelProperty(value = "资源类型0照片 1视频 2其他")
    private Integer resourceType;

    @ApiModelProperty(value = "资源地址")
    private String resourceUrl;
}
