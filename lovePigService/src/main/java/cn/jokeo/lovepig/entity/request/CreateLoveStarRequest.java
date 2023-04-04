package cn.jokeo.lovepig.entity.request;

import cn.hutool.core.date.DatePattern;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author v_chenjinbao_dxm
 * @date 2022/3/24 16:37
 */
@Data
public class CreateLoveStarRequest {
    @ApiModelProperty(value = "空间名称")
    @NotBlank(message = "请填写恋爱空间名称!")
    private String loveName;

    @ApiModelProperty(value = "告白")
    @NotBlank(message = "请填写告白!")
    private String loveMessage;

    @ApiModelProperty(value = "相恋时间")
    @NotNull(message = "请填写相恋时间!")
    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private Date loveTime;

    @ApiModelProperty(value = "发起者")
    private Long inviteUser;

    public void setLoveName(String loveName) {
        this.loveName =  EmojiParser.parseToAliases(loveName);;
    }

    public void setLoveMessage(String loveMessage) {
        this.loveMessage =  EmojiParser.parseToAliases(loveMessage);;
    }
}
