package com.malaxiaoyugan.test;

import com.malaxiaoyugan.test.service.ESClientService;
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

        String settings = "" +
                "  {" +
                "      \"number_of_shards\" : \"1\"," +
                "      \"number_of_replicas\" : \"0\"" +
                "   }";
        String mappings = "{\n" +
                "      \"properties\": {\n" +
                "        \"title\": {\n" +
                "          \"type\": \"text\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"age\": {\n" +
                "          \"type\": \"integer\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        esClientService.createIndex("yuukiindex12", settings, mappings);

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
