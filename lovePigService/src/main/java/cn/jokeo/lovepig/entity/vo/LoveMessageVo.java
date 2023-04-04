package cn.jokeo.lovepig.entity.vo;

import com.vdurmont.emoji.EmojiParser;
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
 * @since 2021-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LoveMessage对象", description = "")
public class LoveMessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long loveId;

    private Long userId;

    @ApiModelProperty(value = "发布人基本信息")
    private UserBaseInfoVo userBase;

    @ApiModelProperty(value = "留言类型1,我们的思念 2.用户留言")
    private Integer messageType;

    @ApiModelProperty(value = "留言内容")
    private String messageContent;

    private Date createTime;

    public void setMessageContent(String messageContent) {
        this.messageContent = EmojiParser.parseToUnicode(messageContent);
    }
}
