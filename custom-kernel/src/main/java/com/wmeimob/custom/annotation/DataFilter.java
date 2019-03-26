package com.wmeimob.custom.annotation;

import java.lang.annotation.*;

/**
 * Created by Shinez on 2016/11/23.
 */

@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFilter {
    String value() default "";
    //数据权限 开启
    boolean filter() default true;

    String tableAlias() default "";
}
