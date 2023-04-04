package cn.jokeo.lovepig.security.annotation;

import java.lang.annotation.*;

/**
 * 用户登录校验
 *
 * @author joke
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PigLogin {
}
