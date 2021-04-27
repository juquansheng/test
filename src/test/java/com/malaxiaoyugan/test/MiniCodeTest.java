package com.malaxiaoyugan.test;

import org.junit.Test;
import java.util.*;

public class MiniCodeTest {


    private static Map<String,List<String>> mapping = new HashMap<>();

    static {
        mapping.put("2", Arrays.asList("A","B","C"));
        mapping.put("3", Arrays.asList("D","E","F"));
        mapping.put("4", Arrays.asList("G","H","I"));
        mapping.put("5", Arrays.asList("J","K","L"));
        mapping.put("6", Arrays.asList("M","N","O"));
        mapping.put("7", Arrays.asList("P","Q","R","S"));
        mapping.put("8", Arrays.asList("T","U","V"));
        mapping.put("9", Arrays.asList("W","X","Y","Z"));
    }


    @Test
    public void test(){
        List<List<String>> arrayLists = new ArrayList<>();
        //组装参数
        int[] arr = {1,2,3,4,5,9};

        for (int a:arr){
            if (mapping.get(Integer.toString(a)) != null){
                arrayLists.add(mapping.get(Integer.toString(a)));
            }
        }
        //返回结果
        List<List<String>> list = getCombination(arrayLists);
        if (list == null || list.size() < 1){
            System.out.println("结果：null");
            return;
        }
        //打印结果
        list.get(0).forEach(a->{
            System.out.println("结果："+a);
        });

    }


    public List<List<String>> getCombination(List<List<String>> arrayLists){

        int len=arrayLists.size();
        //小于2，说明已经递归完成
        if (len<2){
            return arrayLists;
        }
        //拿到第一个数组
        List<String> arr0=arrayLists.get(0);
        int len0=(arrayLists.get(0)).size();


        //拿到第二个数组
        List<String> arr1=arrayLists.get(1);
        int len1=arr1.size();

        //计算当前两个数组一共能够组成多少个组合
        int lenBoth=len0*len1;

        //前两个生成的排列组合保存在这里
        ArrayList<String> tempArrayList=new ArrayList<>(lenBoth);

        //循环arrayLists第一个元素
        for (int i=0;i<len0;i++){
            //循环arrayLists第二个元素
            for (int j=0;j<len1;j++){
                tempArrayList.add(arr0.get(i) + arr1.get(j));
            }
        }
        //这是根据上面排列的结果重新生成的一个集合
        List<List<String>> newArrayLists=new ArrayList<>();
        //把还没排列的数组装进来
        for (int i=2;i<arrayLists.size();i++){
            newArrayLists.add(arrayLists.get(i));
        }
        newArrayLists.add(0,tempArrayList);
        //递归
        return getCombination(newArrayLists);
    }

}
