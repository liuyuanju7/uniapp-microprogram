package cn.jokeo.lovepig.utils;


import java.io.Serializable;

/**
 * 数据响应体
 *
 * @author joke
 * @version 1.0
 * @date 2021/2/25 20:51
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = -6970108747634879221L;

    /**
     * 成功
     */
    public static final int SUCCESS = 1;
    public static final String SUCCESS_MSG = "操作成功!";

    /**
     * 失败
     */
    public static final int FAIL = 0;
    public static final String FAIL_MSG = "操作失败!";

    /**
     * 状态码
     */
    private int code;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 传输数据
     */
    private T data;

    public R() {
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //===================成功
    public static <T> R<T> ok() {
        return new R<T>(SUCCESS, SUCCESS_MSG, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(SUCCESS, SUCCESS_MSG, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<T>(SUCCESS, msg, data);
    }

    //===================失败
    public static <T> R<T> fail() {
        return new R<T>(FAIL, FAIL_MSG, null);
    }

    public static <T> R<T> fail(String msg) {
        return new R<T>(FAIL, msg, null);
    }

    public static <T> R<T> fail(T data) {
        return new R<T>(FAIL, FAIL_MSG, data);
    }

    public static <T> R<T> fail(String msg, T data) {
        return new R<T>(FAIL, msg, data);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R<T>(code, msg, null);
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        return new R<T>(code, msg, data);
    }

    /**
     * 判断返回结果
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> R<T> result(int result) {
        return result > 0 ? new R<T>(SUCCESS, SUCCESS_MSG, null) : new R<T>(FAIL, FAIL_MSG, null);
    }

    /**
     * 判断返回结果
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> R<T> result(boolean result) {
        return result ? new R<>(SUCCESS, SUCCESS_MSG, null) : new R<>(FAIL, FAIL_MSG, null);
    }


    public int getCode() {
        return code;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
