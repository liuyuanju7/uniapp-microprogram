package cn.jokeo.lovepig.entity.enums;

/**
 * @author joke
 * @version 1.0
 * @date 2022/2/19 19:58
 */
public enum ResourceEntityTypeEnum {
    STORY(1, "故事"),
    LOVE_START(2, "恋爱空间"),
    ALBUM(3, "相册");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ResourceEntityTypeEnum() {
    }

    ResourceEntityTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
