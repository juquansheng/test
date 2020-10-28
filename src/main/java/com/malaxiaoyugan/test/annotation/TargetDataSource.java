package com.malaxiaoyugan.test.annotation;

import java.lang.annotation.*;


/**
 * TargetDataSource
 * 自定义数据源选择注解
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value() default "forum";
}
