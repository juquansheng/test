package com.malaxiaoyugan.test.designPattern.decorator;

/**
 * description: Decorator
 * date: 2021/4/1 14:54
 * author: juquansheng
 * version: 1.0 <br>
 */
public abstract class Decorator extends Component {
    protected Component component;
    public Decorator(Component component) {
        this.component = component;
    }
}

