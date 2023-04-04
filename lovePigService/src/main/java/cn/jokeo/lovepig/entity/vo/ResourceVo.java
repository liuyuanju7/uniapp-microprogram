package cn.jokeo.lovepig.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回映射的资源地址
 *
 * @author joke
 * @version 1.0
 * @date 2021/7/13 21:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVo {
    private Long resourceId;

    @ApiModelProperty(value = "资源地址")
    private String resourceUrl;
}
