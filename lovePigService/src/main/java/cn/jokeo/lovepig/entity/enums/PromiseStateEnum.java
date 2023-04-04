package cn.jokeo.lovepig.entity.enums;

/**
 * 承诺类型
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/20 19:34
 */
public enum PromiseStateEnum {


    //承诺状态1创建承诺,2提前结束承诺(必须完成一次),3删除承诺,4未完成承诺,5:完成承诺
    CREATE(1, "创建承诺"),
    EARLY_END(2, "提前结束承诺"),
    REMOVE(3, "删除承诺"),
    UNFINISHED(4, "未完成承诺"),
    FINISH(5, "完成承诺");


    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PromiseStateEnum() {
    }

    PromiseStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
