package com.malaxiaoyugan.test.designPattern.decorator;

/**
 * description: DecoratorA
 * date: 2021/4/1 14:55
 * author: juquansheng
 * version: 1.0 <br>
 */
public class DecoratorA extends Decorator {
    public DecoratorA(Component component) {
        super(component);
    }

    @Override
    public void hello() {
        System.out.println("DecoratorA#hello()");
        this.component.hello();
    }
}

