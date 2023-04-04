package cn.jokeo.lovepig.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询相册信息实体类
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/19 17:05
 */
@Data
public class AlbumVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相册id")
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

    @ApiModelProperty(value = "发布人基本信息")
    private UserBaseInfoVo userBase;


    public void setAlbumName(String albumName) {
        this.albumName = EmojiParser.parseToUnicode(albumName);
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = EmojiParser.parseToUnicode(albumDesc);
    }
}
