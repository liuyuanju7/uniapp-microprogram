package cn.jokeo.lovepig.entity.enums;

/**
 * 承诺类型
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:34
 */
public enum PromiseTypeEnum {


    //promise_state
    PROMISE(1, "普通承诺"),
    MEMORIAL_DAY(2, "纪念日"),
    UNLIMITED_TIME(3, "不限时承诺");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PromiseTypeEnum() {
    }

    PromiseTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
