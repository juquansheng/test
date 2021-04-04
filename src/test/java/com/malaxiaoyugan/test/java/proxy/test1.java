package com.malaxiaoyugan.test.java.proxy;

import com.malaxiaoyugan.test.proxy.CustomInvocationHandler;
import com.malaxiaoyugan.test.service.HelloWorld;
import com.malaxiaoyugan.test.service.impl.HelloWorldImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * description: test1
 * date: 2021/3/22 11:23
 * author: juquansheng
 * version: 1.0 <br>
 */
public class test1 {

    @Test
    public void test(){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloWorldImpl helloWorld = new HelloWorldImpl();
        Class<?>[] interfaces = helloWorld.getClass().getInterfaces();
        System.out.println("interfaces:"+interfaces);
        CustomInvocationHandler handler = new CustomInvocationHandler(new HelloWorldImpl());
        HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(
                test1.class.getClassLoader(),
                new Class[]{HelloWorld.class},
                handler);
        proxy.sayHello("Mikan");
    }


}
