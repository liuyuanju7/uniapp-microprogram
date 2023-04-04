package cn.jokeo.lovepig.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 上传完照片更新相册上传信息
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
@Data
public class ChangeUploadAlbumRequest {

    @ApiModelProperty(value = "相册id")
    @NotNull(message = "提供相册id")
    private Long albumId;

    @ApiModelProperty(value = "本次上传的图片数量")
    @NotNull(message = "提供本次上传的图片数量")
    @Min(message = "最少上传一张图片,你这不对!", value = 1)
    @Max(message = "一次最多只能上传100张,你这不对!", value = 100)
    private Integer imageNumber;

    @ApiModelProperty(value = "相册封面图")
    private String albumBanner;

    @ApiModelProperty(value = "最近一次上传图片的时间")
    private Date lastUploadTime;
}
