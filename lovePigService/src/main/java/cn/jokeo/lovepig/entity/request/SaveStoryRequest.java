package cn.jokeo.lovepig.entity.request;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 储存故事信息
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
public class SaveStoryRequest {

    @ApiModelProperty(value = "恋爱id")
    @NotNull(message = "请提供恋爱空间编号")
    @Min(value = 521, message = "恋爱空间编号不正确!")
    private Long loveId;

    @ApiModelProperty(value = "发布人")
    private Long userId;

    @ApiModelProperty(value = "故事类型1.动态2.文章3.大图文章4.承诺5.视频6.相册")
    @NotNull(message = "请提供故事类型")
    private Integer storyType;

    @ApiModelProperty(value = "头图地址（文章或者承诺使用）")
    private String banner;

    @ApiModelProperty(value = "故事标题（文章使用）")
    private String title;

    @ApiModelProperty(value = "故事内容")
    @NotBlank(message = "故事内容都没有你发个啥!")
    private String content;

    @ApiModelProperty(value = "查看权限0公开1我们俩2私密")
    private Integer seePermissions = 0;

    @ApiModelProperty(value = "是否开放评论0.不开启 1.开启")
    private Integer commentState = 0;

    @ApiModelProperty(value = "发布的位置，经纬度")
    private String location;

    @ApiModelProperty(value = "故事发生时间")
    @JsonFormat(pattern = DatePattern.CHINESE_DATE_PATTERN)
    private Date storyTime;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date publishTime;

    @ApiModelProperty(value = "故事状态0.待发布1.正常2.隐藏3.删除")
    private Integer state;

    @ApiModelProperty(value = "完成承诺记录")
    private FinishPromiseRequest promise;

    public void setContent(String content) {
        this.content =  EmojiParser.parseToAliases(content);
    }

    public void setTitle(String title) {
        this.title = EmojiParser.parseToAliases(title);;
    }
}
