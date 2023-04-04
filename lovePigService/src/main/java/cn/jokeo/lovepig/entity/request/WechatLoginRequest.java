package cn.jokeo.lovepig.entity.request;

import com.vdurmont.emoji.EmojiParser;
import lombok.Data;

/**
 * @author joke
 * @date 2021/8/12 14:44
 */
@Data
public class WechatLoginRequest {
    private String appid;
    private String code;
    //以下是微信用户的信息
    private String nickname;
    private String gender;
    private String avatar;

    public void setNickname(String nickname) {
        this.nickname = EmojiParser.parseToAliases(nickname);
    }
}
