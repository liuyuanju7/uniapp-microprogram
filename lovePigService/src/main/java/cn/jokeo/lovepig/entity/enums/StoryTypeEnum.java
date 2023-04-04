package cn.jokeo.lovepig.entity.enums;

/**
 * @author joke
 * @version 1.0
 * @date 2022/2/19 19:58
 */
public enum StoryTypeEnum {
    GENERAL_USER(0, "普通用户"),
    ALBUM(0, "普通用户"),
    OWNER(1, "主人");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    StoryTypeEnum() {
    }

    StoryTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
