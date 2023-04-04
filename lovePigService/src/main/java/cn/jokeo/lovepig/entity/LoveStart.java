package cn.jokeo.lovepig.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author joke
 * @since 2022-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_start")
@ApiModel(value="LoveStart对象", description="")
public class LoveStart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "恋爱空间id")
    @TableId(value = "love_id", type = IdType.AUTO)
    private Long loveId;

    @ApiModelProperty(value = "空间名称")
    private String loveName;

    @ApiModelProperty(value = "告白的话")
    private String loveMessage;

    @ApiModelProperty(value = "发起者")
    private Long inviteUser;

    @ApiModelProperty(value = "接收者")
    private Long acceptedUser;

    @ApiModelProperty(value = "发起时间")
    private Date inviteTime;

    @ApiModelProperty(value = "接受时间")
    private Date acceptedTime;

    @ApiModelProperty(value = "相恋时间")
    private Date loveTime;

    @ApiModelProperty(value = "分手时间")
    private Date separatedTime;

    @ApiModelProperty(value = "是否允许访问0拒绝1允许")
    private Integer allowAccess;

    @ApiModelProperty(value = "恋爱状态0.发起1.同意2.拒绝5.解除")
    private Integer loveState;

    private Long storyCount;

    private Long messageCount;

    private Long promiseCount;


}
