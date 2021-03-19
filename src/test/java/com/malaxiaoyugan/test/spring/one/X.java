package com.malaxiaoyugan.test.spring.one;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

/**
 * description: X
 * date: 2021/3/8 18:34
 * author: juquansheng
 * version: 1.0 <br>
 */
@Component
public class X {
    @Autowired
    Y y;
    public X(){
        System.out.println("create X");

    }
}
