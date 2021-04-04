package com.malaxiaoyugan.test.spring.one;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * description: test
 * date: 2021/3/8 18:30
 * author: juquansheng
 * version: 1.0 <br>
 */
public class test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        //annotationConfigApplicationContext.setAllowCircularReferences(false);
        annotationConfigApplicationContext.register(AppConfig.class);
        annotationConfigApplicationContext.refresh();
        X bean = annotationConfigApplicationContext.getBean(X.class);
        System.out.println(bean);
    }



}
