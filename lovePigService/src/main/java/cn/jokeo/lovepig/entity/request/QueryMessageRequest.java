package cn.jokeo.lovepig.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class QueryMessageRequest extends PageRequest {
    private long loveId;
    private long messageType;
    @ApiModelProperty(value = "是否查询今天的消息")
    private Boolean todayMessage;

    @ApiModelProperty(value = "被查询的用户id")
    private Long userId;
}
