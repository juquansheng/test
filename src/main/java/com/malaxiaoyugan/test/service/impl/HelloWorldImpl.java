package com.malaxiaoyugan.test.service.impl;

import com.malaxiaoyugan.test.service.HelloWorld;

/**
 * description: HelloWorldImpl
 * date: 2021/3/22 11:25
 * author: juquansheng
 * version: 1.0 <br>
 */
public class HelloWorldImpl implements HelloWorld {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello:" + name);
    }
}
