package cn.jokeo.lovepig.entity.enums;

/**
 * 系统中一些错误的常量
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:34
 */
public enum LoveErrorEnum {

    SAVE_STORY_ERROR(10001, "储存故事信息失败!"),

    //文件错误
    FILE_UPLOAD_ERROR(30001, "上传文件出错!"),
    FILE_TYPE_ERROR(30002, "文件格式不正确!"),

    //用户错误
    USER_WX_OPENID(40001, "获取微信用户信息失败!"),
    USER_REGISTER_FAIL(40002, "用户注册出现异常!"),
    USER_NO_LOGIN(40003, "请登陆后访问!"),
    NOT_LOVE_ID(40004, "未提供要进行操作的恋爱空间id!"),

    //5000系统错误
    LOGIN_CHANNEL(50001, "登录渠道异常!"),

    ;


    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    LoveErrorEnum() {
    }

    LoveErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
