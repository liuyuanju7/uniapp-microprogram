package cn.jokeo.lovepig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author joke
 * @since 2022-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_promise")
@ApiModel(value = "LovePromise对象", description = "")
public class LovePromise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "promise_id", type = IdType.AUTO)
    private Long promiseId;

    @ApiModelProperty(value = "恋爱id")
    private Long loveId;

    @ApiModelProperty(value = "承诺的标题")
    private String promiseName;

    @ApiModelProperty(value = "承诺备注")
    private String promiseRemarks;

    @ApiModelProperty(value = "承诺人")
    private Long userId;

    @ApiModelProperty(value = "承诺类型1.承诺(以后) 2.纪念日(以前) ")
    private Integer promiseType;

    @ApiModelProperty(value = "承诺状态1创建承诺,2结束承诺,3删除承诺,4逾期承诺")
    private Integer promiseState;

    @ApiModelProperty(value = "承诺子类型如恋爱啊,结婚啊等标识性分类")
    private String promiseCategory;

    @ApiModelProperty(value = "承诺开始时间,纪念日的时间")
    private Date promiseTime;

    @ApiModelProperty(value = "目标时间,完成时间")
    private Date targetTime;

    @ApiModelProperty(value = "纪念是提前提醒时间-1代表不需要提醒单位为天0为当天")
    private String promiseRemind;

    @ApiModelProperty(value = "重复的周期")
    private String promiseRepeat;

    @ApiModelProperty(value = "承诺背景图片地址")
    private String promiseBackground;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
