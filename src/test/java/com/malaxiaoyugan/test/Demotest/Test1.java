package com.malaxiaoyugan.test.Demotest;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Test1 {

    @Test
    public void test1(){
       // int [] A = {1,3,6,4,1,2};
        int [] A = {-1,-3,1,3,102};
        int solution = solution(A);
        System.out.println(solution);
    }


    public int solution(int[] A) {
        if (A.length < 1 || A.length > 10000){
            throw new RuntimeException("长度不符合");
        }
        int result = 1;

        Set<Integer> collect = Arrays.stream(A).boxed().collect(Collectors.toSet());

        //降序排列
        Set<Integer> sortSet = new TreeSet<>(Integer::compareTo);
        sortSet.addAll(collect);
        Iterator<Integer> iterator = sortSet.iterator();

        Integer max = 0;
        Integer min = 0;
        if (iterator.hasNext()){
            min = iterator.next();
            max = iterator.next();
        }
        while (iterator.hasNext()){
            max = iterator.next();
        }
        if (min < -1000000 || max > 1000000){
            throw new RuntimeException("数据超出范围");
        }
        if (min < 1) min = 1;
        for (int i = min;i<=max;i++){
            result = i;
            if (!sortSet.contains(i)){
                break;
            }
        }
        if (result == max){
            result = result + 1;
        }
        if (result < 1){
            result = 1;
        }

        return result;
    }
}
