package cn.jokeo.lovepig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("love_promise_record")
@ApiModel(value = "LovePromiseRecord对象", description = "")
public class LovePromiseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long promiseId;

    @ApiModelProperty(value = "承诺名称")
    private String promiseName;

    @ApiModelProperty(value = "所属恋爱空间")
    private Long loveId;

    @ApiModelProperty(value = "第几周年")
    private Integer annual;

    private Long storyId;

    @ApiModelProperty(value = "完成人")
    private Long finishUserId;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;


}
