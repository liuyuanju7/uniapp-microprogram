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
 * @since 2022-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_album")
@ApiModel(value = "Album对象", description = "")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相册id")
    @TableId(value = "album_id", type = IdType.AUTO)
    private Long albumId;

    @ApiModelProperty(value = "所有恋爱空间id")
    private Long loveId;

    @ApiModelProperty(value = "创建的用户")
    private Long userId;

    @ApiModelProperty(value = "相册名称")
    private String albumName;

    @ApiModelProperty(value = "相册描述")
    private String albumDesc;

    @ApiModelProperty(value = "相册封面图")
    private String albumBanner;

    @ApiModelProperty(value = "查看权限0公开1我们俩2私密")
    private Integer albumPermission;

    @ApiModelProperty(value = "相册图片数量")
    private Integer photoNum;

    @ApiModelProperty(value = "最近一次上传图片的时间")
    private Date lastUploadTime;

    @ApiModelProperty(value = "默认相册1是0不是(一个空间只能有一个默认相册)")
    private Integer defaultAlbum;

    @ApiModelProperty(value = "故事状态1.正常2.隐藏3.删除")
    private Integer state;

    @ApiModelProperty(value = "相册创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "相册更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
