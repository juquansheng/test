package com.malaxiaoyugan.test;

import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.es.ESClientService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class TestApplicationTests {

    @Autowired
    private ESClientService esClientService;
    @Test
    void contextLoads() throws IOException {


        JSONObject settingsJsonObject = new JSONObject();
        settingsJsonObject.put("number_of_shards","1");
        settingsJsonObject.put("number_of_replicas","0");

        JSONObject analysisJsonObject = new JSONObject();
        analysisJsonObject.put("analyzer","ik_max_word");
        settingsJsonObject.put("analysis",analysisJsonObject);

        String settings = settingsJsonObject.toJSONString();

        System.out.println("settings:"+settings);

        JSONObject jsonObject = new JSONObject();
        JSONObject propertiesObject = new JSONObject();
        JSONObject titleObject = new JSONObject();
        titleObject.put("type","text");

        propertiesObject.put("properties",titleObject);

        jsonObject.put("properties",propertiesObject);
        String mappings = jsonObject.toJSONString();
        esClientService.createIndex("iktest121g31", settings, mappings);

    }

    @Test
    public void search() throws IOException {
        SearchResponse search = esClientService.search("message", "eventLog", "@timestamp",
                "2020-09-16", "2020-09-18", null, null,".kibana-event-log-7.8.0-000001");
        System.out.println("结果data-------------------------------------------"+search);
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            System.out.println("结果-------------------------------------------");
            System.out.println( documentFields.getSourceAsString());
        }
    }

}
