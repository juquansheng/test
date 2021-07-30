package com.malaxiaoyugan.test.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import java.util.List;

public class BjDingcaijiUtil {

    private static String shangshiurl = "https://www.qcc.com/elib_ipo";
    private static String sanbanurl = "https://www.qcc.com/elib_sanban";


    public static void main(String[] args) {
        System.out.println(sanbanCai(sanbanurl));
//        System.out.println(shangshiCai(shangshiurl));
    }

    //新三板采集
    public static JSONArray sanbanCai(String url) {
        JSONArray resultArray = new JSONArray();
        String result = httpGet(url);
        //获取分页页数
        String pageHtml = result.substring(result.lastIndexOf("pagination pagination-md"), result.length());
        pageHtml = pageHtml.substring(0, pageHtml.indexOf("ul"));
        pageHtml = pageHtml.substring(pageHtml.lastIndexOf("<a"), pageHtml.lastIndexOf("</a>"));
        String page = html2Str(pageHtml);
        for (int i = 1; i <= Integer.parseInt(page); i++) {
            String webUrl = url.substring(0, url.lastIndexOf("/"));
            //新三板
            JSONArray array = sanbanPageCai(webUrl + "/elib_sanban_p_" + i + ".html");
            for (Object obj : array) {
                resultArray.add(obj);
            }
        }
        return resultArray;

    }
    //上市公司采集
    public static JSONArray shangshiCai(String url) {
        JSONArray resultArray = new JSONArray();
        String result = httpGet(url);
        //获取分页页数
        String pageHtml = result.substring(result.lastIndexOf("pagination pagination-md"), result.length());
        pageHtml = pageHtml.substring(0, pageHtml.indexOf("ul"));
        pageHtml = pageHtml.substring(pageHtml.lastIndexOf("<a"), pageHtml.lastIndexOf("</a>"));
        String page = html2Str(pageHtml);
        for (int i = 1; i <= Integer.parseInt(page); i++) {
            String webUrl = url.substring(0, url.lastIndexOf("/"));
            //上市公司
            JSONArray array = shangshiPageCai(webUrl + "/elib_ipo_p_" + i + ".html");
            for (Object obj : array) {
                resultArray.add(obj);
            }
        }
        return resultArray;

    }

    //采集分页数据-上市公司
    public static JSONArray shangshiPageCai(String zUrl) {
        JSONArray resultArray = new JSONArray();
        String result = httpGet(zUrl);
        //解析字段
        String contentHtml = result.substring(result.indexOf("<table class=\"ntable\">"), result.length());
        contentHtml = contentHtml.substring(0, contentHtml.indexOf("</table>"));
        String[] springMsg = contentHtml.split("<tr>");

        Document doc = Jsoup.parse(contentHtml);
        Elements trs = doc.select("table").select("tr");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if (tds.size() > 0) {
                JSONObject object = new JSONObject();
                object.put("xuhao", tds.get(0).text());
                object.put("gupiaoname", tds.get(1).text());
                object.put("gupiaodaima", tds.get(2).text());
                object.put("companyName", tds.get(3).text());
                object.put("jiaoyisuo", tds.get(4).text());
                object.put("shangshidate", tds.get(5).text());
                resultArray.add(object);
            }
        }


        return resultArray;
    }

    //采集分页数据-新三板
    public static JSONArray sanbanPageCai(String zUrl) {
        JSONArray resultArray = new JSONArray();
        String result = httpGet(zUrl);
        //解析字段
        String contentHtml = result.substring(result.indexOf("<table class=\"ntable\">"), result.length());
        contentHtml = contentHtml.substring(0, contentHtml.indexOf("</table>"));
        String[] springMsg = contentHtml.split("<tr>");

        Document doc = Jsoup.parse(contentHtml);
        Elements trs = doc.select("table").select("tr");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if (tds.size() > 0) {
                JSONObject object = new JSONObject();
                object.put("xuhao", tds.get(0).text());
                object.put("gupiaoname", tds.get(1).text());
                object.put("gupiaodaima", tds.get(2).text());
                object.put("companyName", tds.get(3).text());
                object.put("zhuquanshang", tds.get(4).text());
                object.put("zongguben", tds.get(5).text());
                object.put("gupaidate", tds.get(6).text());
                resultArray.add(object);
            }
        }


        return resultArray;
    }

    public static String httpGet(String url) {
        String resultMessage = "";
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        HttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);// 传入URL地址
            httpGet.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");// 设置请求头
            httpGet.addHeader("Accept-Language",
                    "zh-CN,zh;q=0.9");// 设置请求头
            httpGet.addHeader("cookie", "acw_tc=6797958516262300854537162e987fa13c4ef61b9fa2eabcbf62c13173; QCCSESSID=3aeni07akag8mrcemqui8k7b36; UM_distinctid=17aa2ddd9fdcca-03f293315de64d-c3f3568-1fa400-17aa2ddd9fe977; CNZZDATA1254842228=242873011-1626225431-https%253A%252F%252Fwww.baidu.com%252F%7C1626225431; _uab_collina=162623008621060034881953; zg_did=%7B%22did%22%3A%20%2217aa2dddb2dace-0a5b61650bced3-c3f3568-1fa400-17aa2dddb2eb8c%22%7D; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201626230086449%2C%22updated%22%3A%201626230258092%2C%22info%22%3A%201626230086451%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22www.baidu.com%22%7D");// 设置请求头
            httpGet.addHeader(":authority", "www.qcc.com");//设置请求头
            httpGet.addHeader(":method",
                    "GET");// 设置请求头
            httpGet.addHeader(":path",
                    "/elib_sanban_p_2.html");// 设置请求头
            httpGet.addHeader(":scheme",
                    "https");// 设置请求头

            httpGet.addHeader("Referer",
                    "https://www.qcc.com/elib_sanban");// 设置请求头
            httpGet.addHeader("sec-ch-ua",
                    "\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");// 设置请求头
            httpGet.addHeader("sec-ch-ua-mobile", "?0");// 设置请求头
            httpGet.addHeader("sec-fetch-dest", "document");// 设置请求头
            httpGet.addHeader("sec-fetch-mode", "navigate");// 设置请求头
            httpGet.addHeader("sec-fetch-site", "same-origin");// 设置请求头
            httpGet.addHeader("sec-fetch-user", "?1");// 设置请求头
            httpGet.addHeader("sec-fetch-user", "?1");// 设置请求头
            httpGet.addHeader("upgrade-insecure-requests", "1");// 设置请求头
            httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");// 设置请求头

            response = httpClient.execute(httpGet);// 获取响应
            HttpEntity httpEntity = response.getEntity();
            String reponseContent = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);
            resultMessage = reponseContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMessage;

    }

    private static String extractText(Node node) {
        /* TextNode直接返回结果 */
        if (node instanceof TextNode) {
            return ((TextNode) node).text();
        }
        /* 非TextNode的Node，遍历其孩子Node */
        List<Node> children = node.childNodes();
        StringBuffer buffer = new StringBuffer();
        for (Node child : children) {
            buffer.append(extractText(child));
        }
        return buffer.toString();
    }

    /* 使用jsoup解析html并转化为提取字符串*/
    public static String html2Str(String html) {
        Document doc = Jsoup.parse(html);
        return extractText(doc);
    }


}
