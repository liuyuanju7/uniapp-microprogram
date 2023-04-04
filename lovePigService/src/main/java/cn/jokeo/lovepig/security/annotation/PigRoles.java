package cn.jokeo.lovepig.security.annotation;

import cn.jokeo.lovepig.entity.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * 用户角色校验
 *
 * @author joke
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PigRoles {
    /**
     * 用户角色
     *
     * @return
     */
    RoleEnum role() default RoleEnum.OWNER;
}
