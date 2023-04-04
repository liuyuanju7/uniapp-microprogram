package cn.jokeo.lovepig.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询资源需要传递的参数
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 22:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResourcesRequest extends PageRequest {
    private Long loveId;
    private Long entityId;
    private Integer entityType;
    private Integer resourceType;
    /**
     * 是否查询最新的
     */
    private Boolean queryNew;
}
