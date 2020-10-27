package com.malaxiaoyugan.test.mongoDB.controller;



import com.malaxiaoyugan.test.mongoDB.utils.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(value = "测试", tags = "测试")
@Controller
@RequestMapping(value = "test")
@Configuration
@EnableScheduling
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

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


    @ApiOperation(value = "mongoDBTemplate", notes = "mongoDBTemplate")
    @RequestMapping(value = "mongoDBTemplate", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public void mongoDBTemplate(){
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id",9);
        dbObject.put("name","ali");
        dbObject.put("age",26);
        dbObject.put("sex","nv");

        DBObject dbObject1 = new BasicDBObject();
        dbObject1.put("name","腾讯");
        dbObject1.put("address","腾讯");

        dbObject.put("university",dbObject1);

        DBObject dbObject2 = new BasicDBObject();
        dbObject2.put("startDate","2010-10-10");
        dbObject2.put("endDate","2013-06-06");
        dbObject2.put("address","baidu");
        dbObject2.put("name","baidu");

        List<DBObject> dbObjectList = new ArrayList<>();//对象数组
        dbObjectList.add(dbObject2);
        dbObject.put("workExperience",dbObjectList);
        mongoTemplate.getCollection("company_information").insert(dbObject);

        //直接看下结果
        System.out.println(mongoTemplate.getCollection("company_information").findOne(new BasicDBObject().append("_id",8)));
    }

}
