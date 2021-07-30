package com.malaxiaoyugan.test.controller;


import com.malaxiaoyugan.test.utils.DateUtilsByYuuki;
import com.malaxiaoyugan.test.utils.IPUtil;
import com.malaxiaoyugan.test.utils.MongoDBConvertUtils;
import com.malaxiaoyugan.test.utils.RandomStrUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(value = "website")
@RestController
public class CaiJiController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value = "statistics", notes = "访问统计")
    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public void statistics(HttpServletRequest request,String href){

        String ipAddr = IPUtil.getIpAddr(request);
        String addressInfo = IPUtil.getAddressInfo(ipAddr);

        log.info("ipAddr:{},addressInfo:{}",ipAddr,addressInfo);

        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id", RandomStrUtils.getInstance().getRandomString(32));
        dbObject.put("ip",ipAddr);
        dbObject.put("href",href);
        dbObject.put("time", DateUtilsByYuuki.getNowDateString());
        dbObject.put("user_id","null");
        dbObject.put("addressInfo",addressInfo);

        mongoTemplate.getCollection("website_statistics").insertOne(MongoDBConvertUtils.toDocument(dbObject));

        //直接看下结果
        //System.out.println(mongoTemplate.getCollection("website_statistics").find());
    }





}
