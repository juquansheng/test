package com.malaxiaoyugan.test.spring.one;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: Y
 * date: 2021/3/8 18:34
 * author: juquansheng
 * version: 1.0 <br>
 */
@Component
public class Y {
    @Autowired
    X x;

    public Y(){
        System.out.println("create Y");
    }
}
