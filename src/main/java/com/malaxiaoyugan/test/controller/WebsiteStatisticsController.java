package com.malaxiaoyugan.test.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.mongoDB.utils.MongoDBUtil;
import com.malaxiaoyugan.test.utils.*;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;


@RequestMapping(value = "website")
@RestController
public class WebsiteStatisticsController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value = "statistics", notes = "访问统计")
    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public void statistics(HttpServletRequest request,String href){

        String ipAddr = IPUtil.getIpAddr(request);
        String addressInfo = IPUtil.getAddressInfo(ipAddr);

        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", RandomStrUtils.getInstance().getRandomString(32));
        dbObject.put("ip",ipAddr);
        dbObject.put("href",href);
        dbObject.put("time", DateUtilsByYuuki.getNowDateString());
        dbObject.put("user_id","null");
        dbObject.put("addressInfo",addressInfo);

        mongoTemplate.getCollection("website_statistics").insertOne(MongoDBConvertUtils.toDocument(dbObject));

        //直接看下结果
        System.out.println(mongoTemplate.getCollection("website_statistics").find());
    }





}
