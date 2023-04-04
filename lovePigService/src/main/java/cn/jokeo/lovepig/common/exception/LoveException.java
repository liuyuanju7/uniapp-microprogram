package cn.jokeo.lovepig.common.exception;

import cn.jokeo.lovepig.entity.enums.LoveErrorEnum;
import cn.jokeo.lovepig.utils.R;

/**
 * 爱猪猪系统异常类,用于返回异常
 *
 * @author joke
 * @version 1.0
 * @date 2021/6/24 22:47
 */
public class LoveException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code = 50001;

    private String message;

    public LoveException() {
    }

    public LoveException(String message) {
        super(message);
        this.message = message;
    }

    public LoveException(R r) {
        super(r.getMsg());
        this.code = r.getCode();
        this.message = r.getMsg();
    }

    public LoveException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public LoveException(LoveErrorEnum loveErrorEnum) {
        this.code = loveErrorEnum.getCode();
        this.message = loveErrorEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 将异常信息封住成返回对象
     *
     * @return
     */
    public R getResult() {
        return new R(code, message, null);
    }
}
