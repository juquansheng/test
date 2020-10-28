package com.malaxiaoyugan.test.annotation;

import java.lang.reflect.Method;

/**
 * 得到方法上的权限注解
 */
public class AnnotationParse {

    public static String privilegeParse(Method method) throws Exception {
        //获取该方法
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
            return annotation.value();
        }
        return null;
    }
}
