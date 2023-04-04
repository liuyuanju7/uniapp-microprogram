package cn.jokeo.lovepig.common.exception;

import cn.jokeo.lovepig.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 处理Rest请假接口出现的异常
 *
 * @author joke
 * @version 1.0
 * @date 2021/3/1 11:30
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(LoveException.class)
    public R handleException(LoveException e) {
        logger.error("用户操作发生异常: ", e);
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 数据校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R handleException(BindException e) {
       /*
            //抛出的异常可能不止一个
            List<ObjectError> errors = e.getAllErrors();
            //获取第一个异常
            ObjectError error = errors.get(0);
            //获取异常信息
            String errorMsg = error.getDefaultMessage();
        */
        FieldError fieldError = e.getFieldError();
        logger.error("请求参数绑定异常: ", e);
        return R.fail(5001, fieldError.getDefaultMessage());
    }

    /**
     * 数据校验异常
     * 处理当对用@Valid注释的参数进行验证失败时，将引发异常
     * 处理没有使用@RequestBody的参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        logger.error("请求参数绑定异常: ", e);
        return R.fail(5001, errorList.get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error("服务器发生异常: ", e);
        return R.fail(5000, "系统打了个盹!请稍后再试!");
    }
}
