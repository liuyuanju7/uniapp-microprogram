package cn.jokeo.lovepig.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 上传资源需要传递的参数
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 22:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResourcesRequest {
    @NotNull
    @Min(value = 521, message = "恋爱空间id不正确")
    private Long loveId;
    @NotNull(message = "实体id不能为空")
    @Min(value = 1, message = "实体id不正确")
    private Long entityId;
    @NotNull(message = "实体类型不能为空")
    private Integer entityType;
    /**
     * 文件地址=传递参数的字段
     */
    private String resourceUrl;
    private Integer resourceType;
    private Integer resourceSize;
}
