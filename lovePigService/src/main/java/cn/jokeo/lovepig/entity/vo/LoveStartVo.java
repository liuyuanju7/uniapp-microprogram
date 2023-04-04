package cn.jokeo.lovepig.entity.vo;

import cn.jokeo.lovepig.entity.UserBase;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:09
 */
@Data
public class LoveStartVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "恋爱空间id")
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

    @ApiModelProperty(value = "发起者")
    private UserBase inviteUserInfo;

    @ApiModelProperty(value = "接收者")
    private UserBase acceptedUserInfo;
    /**
     * 恋爱天数
     */
    @ApiModelProperty(value = "恋爱天数")
    private Long loveDay;
    /**
     * 首页轮播图
     */
    private List<ResourceVo> banners;

    public void setLoveName(String loveName) {
        this.loveName =  EmojiParser.parseToUnicode(loveName);
    }

    public void setLoveMessage(String loveMessage) {
        this.loveMessage =  EmojiParser.parseToUnicode(loveMessage);
    }
}