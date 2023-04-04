package cn.jokeo.lovepig.entity.enums;

/**
 * 用户角色
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:34
 */
public enum RoleEnum {

    GENERAL_USER(0, "普通用户"),
    OWNER(1, "主人");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    RoleEnum() {
    }

    RoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
