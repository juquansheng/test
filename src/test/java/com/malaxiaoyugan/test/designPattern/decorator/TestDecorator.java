package com.malaxiaoyugan.test.designPattern.decorator;

/**
 * description: 装饰模式   应用场景 BufferedReader 聚合了 Reader
 * date: 2021/4/1 14:55
 * author: juquansheng
 * version: 1.0 <br>
 */
public class TestDecorator {
    public static void main(String[] args) {
        Component component = new ComponentA(); //待装饰对象

        component = new DecoratorA(component);  //使用 DecoratorA 对 component 进行装饰
        component = new DecoratorB(component);  //使用 DecoratorB 对 component 进行装饰

        component.hello();
    }
}

