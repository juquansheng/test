package com.malaxiaoyugan.test.designPattern;

/**
 * description: Test
 * date: 2021/3/30 10:25
 * author: juquansheng
 * version: 1.0 <br>
 */
public class Test {

    @org.junit.Test
    public void test(){
        Computer.Builder builder = new Computer.Builder("1", "1");
        System.out.println(builder);
        builder.setDisplay("1");
        builder.build();
        System.out.println(builder);


        Computer.Builder builder1 = Computer.builder("", "").setDisplay("");

    }
}
