package com.malaxiaoyugan.test;

import org.assertj.core.util.Sets;
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
        int[] arr9 = {1,2,3,4,5,9};
        letters9(arr9);
        int[] arr99 = {23,92};
        letters99(arr99);
    }


    public void letters9(int[] arr){
        List<List<String>> arrayLists = new ArrayList<>();

        for (int a:arr){
            if (a < 0){
                System.out.println("参数有误："+a+"小于0");
                return;
            }
            if (a > 9){
                System.out.println("参数有误："+a+"大于9");
                return;
            }
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
        System.out.println("----------letters9结果：");
        list.get(0).forEach(a->{
            System.out.print(""+a+" ");
        });
        System.out.println();
        System.out.println("----------letters9结束：");
    }

    public void letters99(int[] arr){
        List<List<String>> arrayLists = new ArrayList<>();

        for (int a:arr){

            if (a < 0){
                System.out.println("参数有误："+a+"小于0");
                return;
            }
            if (a > 99){
                System.out.println("参数有误："+a+"大于99");
                return;
            }

            List<String> list = getList(a);
            arrayLists.add(list);
        }
        //返回结果
        List<List<String>> list = getCombination(arrayLists);
        if (list == null || list.size() < 1){
            System.out.println("结果：null");
            return;
        }
        //打印结果
        System.out.println("----------letters99结果：");
        list.get(0).forEach(a->{
            System.out.print(""+a+" ");
        });
        System.out.println();
        System.out.println("----------letters99结束：");
    }

    //根据数字获取参数数据  例如26 既 A B C + M N O
    private List<String> getList(int params){
        List<String> list = new ArrayList<>();

        String s = Integer.toString(params);
        int length = s.length();
        for (int i=0;i<length;i++){
            String substring = s.substring(i, i + 1);
            if (mapping.get(substring) != null){
                list.addAll(mapping.get(substring));
            }
        }
        HashSet<String> strings = Sets.newHashSet(list);
        list.clear();
        list.addAll(strings);
        return list;
    }

    //根据数组获取排列组合结果
    private List<List<String>> getCombination(List<List<String>> arrayLists){

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
