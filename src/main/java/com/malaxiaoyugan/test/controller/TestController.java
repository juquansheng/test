package com.malaxiaoyugan.test.controller;


import com.malaxiaoyugan.test.mongoDB.utils.MongoDBUtil;
import com.malaxiaoyugan.test.utils.DateUtil;
import com.malaxiaoyugan.test.utils.MongoDBConvertUtils;
import com.malaxiaoyugan.test.utils.RandomStrUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.swagger.annotations.ApiOperation;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping(value = "test")
@RestController
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

}
