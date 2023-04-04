package cn.jokeo.lovepig.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表和其他标的关联
 * </p>
 *
 * @author joke
 * @since 2022-02-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resource_relate")
@ApiModel(value="ResourceRelate对象", description="资源表和其他标的关联")
public class ResourceRelate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联id")
    @TableId(value = "relate_id", type = IdType.AUTO)
    private Long relateId;

    @ApiModelProperty(value = "关联的实体类型1=story 2=loveStart 3=album")
    private Integer entityType;

    @ApiModelProperty(value = "关联实体id")
    private Long entityId;

    @ApiModelProperty(value = "资源id")
    private Long resourceId;

    @ApiModelProperty(value = "关联的资源地址(冗余引入)")
    private String resourceUrl;


}
