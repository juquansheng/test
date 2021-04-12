package com.malaxiaoyugan.test.designPattern.decorator;

/**
 * description: DecoratorB
 * date: 2021/4/1 14:55
 * author: juquansheng
 * version: 1.0 <br>
 */
public class DecoratorB extends Decorator {
    public DecoratorB(Component component) {
        super(component);
    }

    @Override
    public void hello() {
        System.out.println("DecoratorB#hello()");
        this.component.hello();
    }
}

