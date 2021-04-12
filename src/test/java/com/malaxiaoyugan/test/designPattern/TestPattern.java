package com.malaxiaoyugan.test.designPattern;

import org.junit.Test;

/**
 * description: Test
 * date: 2021/3/30 10:25
 * author: juquansheng
 * version: 1.0 <br>
 */
public class TestPattern {

    //建造者
    @Test
    public void test(){
        Computer.Builder builder = new Computer.Builder("1", "1");
        System.out.println(builder);
        builder.setDisplay("1");
        builder.build();
        System.out.println(builder);


        Computer.Builder builder1 = Computer.builder("", "").setDisplay("");

    }
}
