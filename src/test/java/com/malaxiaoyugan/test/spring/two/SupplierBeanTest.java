package com.malaxiaoyugan.test.spring.two;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * description: SupplierBeanTest
 * date: 2021/3/9 10:30
 * author: juquansheng
 * version: 1.0 <br>
 */
public class SupplierBeanTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(User.class);
        beanDefinition.setInstanceSupplier(SupplierBeanTest::createUser);
        context.registerBeanDefinition(User.class.getSimpleName(), beanDefinition);
        context.refresh();
        System.out.println(context.getBean(User.class).getName());
    }

    private static User createUser(){
        return new User("小薇呀");
    }

    static class User{
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @PostConstruct
        public void init(){
            System.out.println("user 初始化.....");
        }
    }

}
