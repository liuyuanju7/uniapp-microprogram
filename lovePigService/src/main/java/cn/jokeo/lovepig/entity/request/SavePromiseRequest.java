package cn.jokeo.lovepig.entity.request;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class SavePromiseRequest {
    @ApiModelProperty(value = "修改的承诺id编辑时使用")
    private Long promiseId;

    @ApiModelProperty(value = "恋爱id")
    @NotNull(message = "请提供恋爱空间编号")
    @Min(value = 521, message = "恋爱空间编号不正确!")
    private Long loveId;

    @ApiModelProperty(value = "承诺的标题")
    @NotBlank(message = "承诺的主题不能为空")
    private String promiseName;

    @ApiModelProperty(value = "承诺备注")
    private String promiseRemarks;

    @ApiModelProperty(value = "承诺人")
    private Long userId;

    @ApiModelProperty(value = "承诺类型1.承诺(以后) 2.纪念日(以前) ")
    @NotNull(message = "承诺类型不能为空")
    private Integer promiseType;

    @ApiModelProperty(value = "承诺子类型如恋爱啊,结婚啊等标识性分类")
    private String promiseCategory;

    @ApiModelProperty(value = "承诺开始时间,纪念日的时间")
    @NotNull(message = "承诺开始时间不能为空!")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private Date promiseTime;

    @ApiModelProperty(value = "纪念是提前提醒时间-1代表不需要提醒单位为天0为当天")
    @NotBlank(message = "提前提醒时间不能为空!")
    private String promiseRemind;

    @ApiModelProperty(value = "重复的周期")
    @NotEmpty(message = "重复的周期不能为空!")
    private String promiseRepeat;

    @ApiModelProperty(value = "承诺背景图片地址")
    private String promiseBackground;

    public void setPromiseName(String promiseName) {
        this.promiseName = EmojiParser.parseToAliases(promiseName);
    }

    public void setPromiseRemarks(String promiseRemarks) {
        this.promiseRemarks = EmojiParser.parseToAliases(promiseRemarks);
    }
}
