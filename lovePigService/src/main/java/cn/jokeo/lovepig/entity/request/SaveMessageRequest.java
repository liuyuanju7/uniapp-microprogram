package cn.jokeo.lovepig.entity.request;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 储存故事信息
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/19 18:58
 */
@Data
@NoArgsConstructor
public class SaveMessageRequest {

    @ApiModelProperty(value = "留言类型1.思念 2.用户留言")
    @NotNull(message = "留言类型提供下!")
    private Integer messageType;

    @ApiModelProperty(value = "故事内容")
    @NotBlank(message = "你不说点啥啊!")
    private String messageContent;

    public void setMessageContent(String messageContent) {
        this.messageContent = EmojiParser.parseToAliases(messageContent);
    }
}
