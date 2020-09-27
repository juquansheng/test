package com.malaxiaoyugan.test;

import com.malaxiaoyugan.test.utils.ConcurrentCalculator;
import com.malaxiaoyugan.test.utils.ConcurrentCalculatorAsync;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * description: ThreadTest
 * date: 2020/9/27 14:10
 * author: juquansheng
 * version: 1.0 <br>
 */
@SpringBootTest
public class ThreadTest {


    /**
     * 获取CPU核心个数
     */
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
        int[] numbers = new int[]{1,2,5,6,8,5,4,454,5,4};
        ConcurrentCalculatorAsync concurrentCalculator = new ConcurrentCalculatorAsync();
        Long sum = concurrentCalculator.sum(numbers);
        System.out.println(sum);
    }


}
