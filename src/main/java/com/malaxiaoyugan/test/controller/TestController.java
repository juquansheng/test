package com.malaxiaoyugan.test.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.mongoDB.utils.MongoDBUtil;
import com.malaxiaoyugan.test.utils.DateUtil;
import com.malaxiaoyugan.test.utils.MongoDBConvertUtils;
import com.malaxiaoyugan.test.utils.RandomStrUtils;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;


@Slf4j
@RequestMapping(value = "test")
@RestController
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;


    @ApiOperation(value = "test", notes = "test")
    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String test(String name){
        log.info("test:"+name);
        return "test:"+name;
    }

    @ApiOperation(value = "test2", notes = "test2")
    @RequestMapping(value = "test2", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String test2(@RequestBody JSONObject jsonObject){
        log.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }


    @ApiOperation(value = "mongoDBTest", notes = "mongoDBTest")
    @RequestMapping(value = "mongoDBTest", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public void mongoDBTest(){

        //获取集合
        MongoCollection<Document> collection = MongoDBUtil.getConnectWithCredential().getCollection("company_information");

        //创建文档
        Document document = new Document("name","张三aaa")
                .append("sex", "男")
                .append("age", 20);
        //插入一个文档
        collection.insertOne(document);
    }


    @ApiOperation(value = "mongoDBTestFind", notes = "mongoDBTestFind")
    @RequestMapping(value = "mongoDBTestFind", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public void mongoDBTestFind() {

        //获取集合
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection("company_information");

        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());

        }
    }


    @RequestMapping(value = "mongoDBTemplate", method = RequestMethod.GET)
    public void mongoDBTemplate(){
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", RandomStrUtils.getInstance().getRandomString());
        dbObject.put("name","ali");
        dbObject.put("age",26);
        dbObject.put("sex","nv");

        DBObject dbObject1 = new BasicDBObject();
        dbObject1.put("name","腾讯");
        dbObject1.put("address","腾讯");

        dbObject.put("university",dbObject1);

        DBObject dbObject2 = new BasicDBObject();
        dbObject2.put("startDate", DateUtil.getCurrentDate());
        dbObject2.put("endDate",DateUtil.getCurrentDate());
        dbObject2.put("address","baidu");
        dbObject2.put("name","baidu");

        List<DBObject> dbObjectList = new ArrayList<>();//对象数组
        dbObjectList.add(dbObject2);
        dbObject.put("workExperience",dbObjectList);


        mongoTemplate.getCollection("company_information").insertOne(MongoDBConvertUtils.toDocument(dbObject));

        //直接看下结果
        System.out.println(mongoTemplate.getCollection("company_information").find(new BasicDBObject().append("_id",8)));
    }

    @RequestMapping(value = "mongoServiceTest", method = RequestMethod.GET)
    public void mongoServiceTest(){
        Aggregation aggregation =
                Aggregation.newAggregation(
                        Aggregation.unwind("data.datas"),
                        Aggregation.match(Criteria.where("_id").gt(0).lt(100000).and("data.datas.abnormalType").is("无法联系")),
                        Aggregation.group("_id").count().as("count")
                ).withOptions(newAggregationOptions().cursor(new Document()).build());


        //查询方法一
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "abnormal", Document.class);
        Document rawResults = aggregate.getRawResults();
        List<Document> result = rawResults.getList("result", Document.class);
        System.out.println(result.toString());

        //---------------------------------------------------
        //查询方法二
        AggregationOptions build = AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build();
        List<Document> pipeLine = new ArrayList<>();
        JSONArray pipelineJSONArray = JSONObject.parseObject(aggregation.toString()).getJSONArray("pipeline");

        for (Object objects:pipelineJSONArray){
            String toJSONString = JSONObject.toJSONString(objects);
            pipeLine.add(MongoDBConvertUtils.toDocument(JSONObject.parseObject(toJSONString)));
        }
        AggregateIterable<Document> documents = mongoTemplate.getCollection("abnormal").aggregate(pipeLine);
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            System.out.println("cursor"+iterator.next());

        }
    }

}
