package com.malaxiaoyugan.test.pdf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import technology.tabula.CommandLineApp;

import java.io.*;
import java.util.Collection;

/**
 * description: PDFTest
 * date: 2021/7/30 10:57
 * author: juquansheng
 * version: 1.0 <br>
 */
@SpringBootTest
public class PDFTest {

    @Test
    public void test1() throws ParseException {

        //输出文件
        //String[] args = new String[]{"-f=JSON","-o=d:/output.txt", "-p=all", "C:\\Users\\dell\\Desktop\\test\\table.pdf"};
        //CommandLineApp.main(args);
        //输出控制台
        String[] args = new String[]{"-f=JSON","-p=all","C:\\Users\\dell\\Desktop\\test\\table2.pdf"};
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CommandLineApp.buildOptions(), args);
        StringBuilder stringBuilder = new StringBuilder();
        new CommandLineApp(stringBuilder, cmd).extractTables(cmd);
        System.out.println("============");
        System.out.println(stringBuilder.toString());

        //提取数据
        StringBuilder result = new StringBuilder();
        String toString = stringBuilder.toString();
        JSONArray jsonArray = JSON.parseArray(toString);
        if (jsonArray == null || jsonArray.size() < 1){
            return;
        }
        for (int i = 0;i < jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
            //提取方式
            String extractionMethod = jsonObject.getString("extraction_method");
            //如果页面有表格，则进行数据替换
            if ("lattice".equals(extractionMethod)){
                //页面所有内容
                JSONObject object = getObject(i);
                JSONArray jsonArrayNew = object.getJSONArray("data");
                //表格所在位置需要替换
                Float top = jsonObject.getFloat("top");
                Float bottom = jsonObject.getFloat("bottom");
                boolean isDone = false;
                for (Object o1 :jsonArrayNew){
                    JSONArray jsonArray2 = JSON.parseArray(o1.toString());
                    for (Object o2 :jsonArray2){
                        JSONObject jsonObject1 = JSON.parseObject(o2.toString());
                        String text = jsonObject1.getString("text");
                        Float top1 = jsonObject1.getFloat("top");

                        if (top <= top1 && bottom >= top1 && !isDone){//范围内替换
                            isDone = true;
                            for (Object o3 :jsonArray1){
                                JSONArray jsonArray4 = JSON.parseArray(o3.toString());
                                for (Object o4 :jsonArray4){
                                    JSONObject jsonObject5 = JSON.parseObject(o4.toString());
                                    String text1 = jsonObject5.getString("text");
                                    if (StringUtils.isNotEmpty(text1)){
                                        result.append(text1).append(" ");
                                    }

                                }
                            }

                        }
                        if (StringUtils.isNotEmpty(text)){
                            result.append(text).append(" ");
                        }

                    }
                }
            }else {
                for (Object o1 :jsonArray1){
                    JSONArray jsonArray2 = JSON.parseArray(o1.toString());
                    for (Object o2 :jsonArray2){
                        JSONObject jsonObject1 = JSON.parseObject(o2.toString());
                        String text = jsonObject1.getString("text");
                        if (StringUtils.isNotEmpty(text)){
                            result.append(text).append(" ");
                        }

                    }
                }
            }

        }
        System.out.println("============");
        System.out.println(result);
        System.out.println(result.toString());

    }


    //获取流读取方式,按行读取全部
    private JSONObject getObject(int index) throws ParseException {
        String[] args = new String[]{"-f=JSON","-p=all","C:\\Users\\dell\\Desktop\\test\\table2.pdf","-t"};
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CommandLineApp.buildOptions(), args);
        StringBuilder stringBuilder = new StringBuilder();
        new CommandLineApp(stringBuilder, cmd).extractTables(cmd);
        System.out.println("============");
        System.out.println(stringBuilder.toString());

        //提取数据
        StringBuilder result = new StringBuilder();
        String toString = stringBuilder.toString();
        JSONArray jsonArray = JSON.parseArray(toString);
        JSONObject jsonObject = jsonArray.getJSONObject(index);
        return jsonObject;
    }

}
