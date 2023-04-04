package cn.jokeo.lovepig.entity.request;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author joke
 * @since 2022-02-20
 */
@Data
public class CreateAlbumRequest {

    @ApiModelProperty(value = "所有恋爱空间id")
    @NotNull(message = "请提供恋爱空间编号")
    @Min(value = 521, message = "恋爱空间编号不正确!")
    private Long loveId;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "相册名称")
    @NotNull(message = "请提供相册名称")
    private String albumName;

    @ApiModelProperty(value = "相册描述")
    private String albumDesc;

    @ApiModelProperty(value = "相册封面图")
    private String albumBanner;

    @ApiModelProperty(value = "查看权限0公开1我们俩2私密")
    private Integer albumPermission;

    public void setAlbumName(String albumName) {
        this.albumName = EmojiParser.parseToAliases(albumName);
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = EmojiParser.parseToAliases(albumDesc);
    }
}
