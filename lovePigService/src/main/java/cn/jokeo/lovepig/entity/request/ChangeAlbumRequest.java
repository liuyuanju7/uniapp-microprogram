package cn.jokeo.lovepig.entity.request;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
public class ChangeAlbumRequest {

    @ApiModelProperty(value = "相册id")
    @NotNull(message = "请提供要修改的相册id")
    private Long albumId;

    @ApiModelProperty(value = "相册名称")
    @NotNull(message = "请提供相册名称")
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

    public void setAlbumName(String albumName) {
        this.albumName = EmojiParser.parseToAliases(albumName);
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = EmojiParser.parseToAliases(albumDesc);
    }
}
