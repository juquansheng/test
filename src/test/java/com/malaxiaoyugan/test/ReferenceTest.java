package com.malaxiaoyugan.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * description: ReferenceTest
 * date: 2021/3/5 17:31
 * author: juquansheng
 * version: 1.0 <br>
 */
@SpringBootTest
public class ReferenceTest {
    static Object object = new Object();
    @Test
    public void test1(){

        /**
         * 强引用
         */
            Object obj = object;
            object = null;
            System.gc();
            System.out.print("after system.gc-strongReference---obj = " + obj);


    }
}
