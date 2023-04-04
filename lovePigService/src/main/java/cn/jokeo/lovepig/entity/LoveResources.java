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
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_resources")
@ApiModel(value = "LoveResources对象", description = "")
public class LoveResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @TableId(value = "resource_id", type = IdType.AUTO)
    private Long resourceId;

    @ApiModelProperty(value = "恋爱id")
    private Long loveId;

    @ApiModelProperty(value = "资源类型0照片 1视频 2其他")
    private Integer resourceType;

    @ApiModelProperty(value = "上传的用户")
    private Long userId;

    @ApiModelProperty(value = "资源地址")
    private String resourceUrl;

    @ApiModelProperty(value = "资源大小")
    private Integer resourceSize;

    @ApiModelProperty(value = "上传的内容是否展示0，不展示 1，展示")
    private Integer display;

    @ApiModelProperty(value = "资源状态1.正常0.删除")
    private Integer state;
    
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
