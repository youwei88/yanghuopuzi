package com.wmeimob.custom.annotation;

import com.wmeimob.custom.enums.TokenType;

import java.lang.annotation.*;

/**
 * Created by xiangzhao on 2016/8/4.
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * 默认创建，如需验证请设置其他值
     *
     * @return
     */
    TokenType type() default TokenType.VALIDATE;

    String[] postExcludes() default {};

    String[] getExcludes() default {};

    String requestRoot() default "";

    boolean clean() default false;


}
