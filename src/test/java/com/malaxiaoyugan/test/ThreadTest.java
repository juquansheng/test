/*
package com.malaxiaoyugan.test;

import com.malaxiaoyugan.test.utils.ConcurrentCalculator;
import com.malaxiaoyugan.test.utils.ConcurrentCalculatorAsync;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

*/
/**
 * description: ThreadTest
 * date: 2020/9/27 14:10
 * author: juquansheng
 * version: 1.0 <br>
 *//*

@SpringBootTest
public class ThreadTest {


    */
/**
     * 获取CPU核心个数
     *//*

    @Test
    public void getProcessors(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void test1(){
        int[] numbers = new int[]{1,2,5,6,8,5,4,454,5,4};
        ConcurrentCalculator concurrentCalculator = new ConcurrentCalculator();
        Integer sum = concurrentCalculator.sumNew(numbers);
        System.out.println(sum);
    }

    @Test
    public void test2(){
        //int[] numbers = new int[]{1,2,5,6,8,5,4,454,5,4};
        int n = 20;
        int[] numbers = new int[n];
        for(int i=1;i<=n;i++){
            numbers[i-1]=i;
        }
        System.out.println(numbers.length);
        ConcurrentCalculatorAsync concurrentCalculator = new ConcurrentCalculatorAsync();
        Long sum = concurrentCalculator.sum(numbers);
        System.out.println(sum);
    }

    @Test
    public void test3(){
        List<String> stringList = new ArrayList<>();
        for (int i = 0;i < 100;i++){
            stringList.add(i+"1");
        }

        final CountDownLatch countDownLatch = new CountDownLatch(stringList.size());
        ExecutorService pool = Executors.newFixedThreadPool(20);
        final AtomicInteger ai = new AtomicInteger(0);
        AtomicReference<Integer> result = new AtomicReference<>(0);
        for (final String s : stringList) {
            Runnable run = () -> {
                try {
                    System.out.println("执行" + s);
                    String res = sum(s);
                    Integer amount = res.length();
                    result.updateAndGet(v -> v + amount);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            };
            pool.execute(run);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } // 等到alist里的数据跑完以后 才会执行后续的方法
        System.out.println("result1:" + result);
        pool.shutdown();
        System.out.println("result2:" + result);

    }

    private String sum(String s){
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(10000));
        }catch (Exception e){

        }
        return s+"1";
    }

    @Test
    public void test4(){
        Random random = new Random();
        System.out.println(random.nextInt(1000));
    }

}
*/
