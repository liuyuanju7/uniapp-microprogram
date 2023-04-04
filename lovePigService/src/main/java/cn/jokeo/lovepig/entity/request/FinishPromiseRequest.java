package cn.jokeo.lovepig.entity.request;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 储存承诺完成记录
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class FinishPromiseRequest {


    @ApiModelProperty(value = "承诺id")
    private Long promiseId;

    @ApiModelProperty(value = "承诺id")
    private String promiseName;

    @ApiModelProperty(value = "第几周年")
    private Integer annual;

    public void setPromiseName(String promiseName) {
        this.promiseName = EmojiParser.parseToAliases(promiseName);
    }
}
