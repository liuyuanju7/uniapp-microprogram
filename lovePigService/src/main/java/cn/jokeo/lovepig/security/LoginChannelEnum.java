package cn.jokeo.lovepig.security;

import cn.jokeo.lovepig.security.process.WechatLoginProcess;
import cn.jokeo.lovepig.utils.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统中一些错误的常量
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:34
 */
public enum LoginChannelEnum {

    //文件错误
    WECHAT("wechat", "wx" + Constants.TOKEN_JOIN, WechatLoginProcess.class);

    /**
     * 所有通道的map
     */
    public static Map<String, Class> channelMap = new HashMap<>();

    static {
        channelMap.put(WECHAT.prefix, WECHAT.process);
    }

    /**
     * 通道
     */
    private String channel;

    /**
     * token前缀
     */
    private String prefix;
    /**
     * 对应的处理类
     */
    private Class process;

    public String getChannel() {
        return channel;
    }

    public String getPrefix() {
        return prefix;
    }

    LoginChannelEnum(String channel, String prefix, Class process) {
        this.channel = channel;
        this.prefix = prefix;
        this.process = process;
    }
}
