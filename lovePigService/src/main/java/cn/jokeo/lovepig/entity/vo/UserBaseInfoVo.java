package cn.jokeo.lovepig.entity.vo;

import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author joke
 * @version 1.0
 * @date 2021/7/13 21:06
 */
@Data
public class UserBaseInfoVo {

    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别0，未设置，1男，2女")
    private Integer gender;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    public void setNickname(String nickname) {
        this.nickname =  EmojiParser.parseToUnicode(nickname);
    }
}
