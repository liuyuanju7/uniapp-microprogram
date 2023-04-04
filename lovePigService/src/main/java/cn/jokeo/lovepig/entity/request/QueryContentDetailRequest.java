package cn.jokeo.lovepig.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
public class QueryContentDetailRequest extends PageRequest {
    @NotNull(message = "请提供你要查询的恋爱空间id")
    @Min(value = 521, message = "恋爱空间不存在")
    private Long loveId;

    @NotNull(message = "请提供你要查询的相册id")
    @Min(value = 1, message = "相册不存在")
    private Long albumId;
}
