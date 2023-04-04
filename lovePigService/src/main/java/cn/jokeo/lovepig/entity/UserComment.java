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
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_comment")
@ApiModel(value = "UserComment对象", description = "")
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "恋爱id")
    private Long loveId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "评论类型1:帖子 2:用户 ")
    private Integer entityType;

    @ApiModelProperty(value = "具体那个故事或者用户id")
    private Long entityId;

    @ApiModelProperty(value = "嵌套回复时回复的目标")
    private Long targetId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "评论附加的一张图片")
    private String image;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
