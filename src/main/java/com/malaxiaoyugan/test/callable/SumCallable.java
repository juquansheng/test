package com.malaxiaoyugan.test.callable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;

/**
 * description: SumCallable
 * date: 2020/9/27 17:00
 * author: juquansheng
 * version: 1.0 <br>
 */
public class SumCallable implements Callable<Integer> {
    private int[] numbers;
    public SumCallable(final int[] numbers) {
        this.numbers = numbers;
    }


    @Override
    public Integer call() throws Exception {
        Integer sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
