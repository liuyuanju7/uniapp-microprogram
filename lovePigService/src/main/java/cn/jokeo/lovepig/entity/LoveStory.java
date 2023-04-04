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
 * 故事
 * </p>
 *
 * @author joke
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_story")
@ApiModel(value = "LoveStory对象", description = "故事")
public class LoveStory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "故事id")
    @TableId(value = "story_id", type = IdType.AUTO)
    private Long storyId;

    @ApiModelProperty(value = "恋爱id")
    private Long loveId;

    @ApiModelProperty(value = "发布人")
    private Long userId;

    @ApiModelProperty(value = "故事类型1.动态2.文章3.大图文章4.承诺5.视频6.相册")
    private Integer storyType;

    @ApiModelProperty(value = "故事标题（文章使用）")
    private String title;

    @ApiModelProperty(value = "头图地址（文章或者承诺使用）")
    private String banner;

    @ApiModelProperty(value = "故事摘要")
    private String summary;

    @ApiModelProperty(value = "故事内容")
    private String content;

    @ApiModelProperty(value = "查看权限0公开1我们俩2私密")
    private Integer seePermissions;

    @ApiModelProperty(value = "是否开放评论0.不开启 1.开启")
    private Integer commentState;

    @ApiModelProperty(value = "评论数量")
    private Integer commentNum;

    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;

    @ApiModelProperty(value = "发布的位置，经纬度")
    private String location;

    @ApiModelProperty(value = "天气")
    private String weather;

    @ApiModelProperty(value = "故事发生时间")
    @TableField(fill = FieldFill.INSERT)
    private Date storyTime;

    @ApiModelProperty(value = "发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date publishTime;

    @ApiModelProperty(value = "故事状态0.待发布1.正常2.隐藏3.删除")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
