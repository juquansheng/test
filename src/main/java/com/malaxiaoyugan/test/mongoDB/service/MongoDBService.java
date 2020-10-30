package com.malaxiaoyugan.test.mongoDB.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.utils.MongoDBConvertUtils;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;

/**
 * description: MongoDBService
 * date: 2020/10/27 10:02
 * author: juquansheng
 * version: 1.0 <br>
 */
@Service
public class MongoDBService {
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 保存数据 save 或者 insert
     * insert: 若新增数据的主键已经存在，则会抛 org.springframework.dao.DuplicateKeyException 异常提示主键重复，
     * 可以一次插入多条数据
     × save: 若新增数据的主键已经存在，则会对当前已经存在的数据进行修改操作。只能save一条数据
     * @param dbObject
     */
    public void saveOne(Bson dbObject, String collectionName){
        mongoTemplate.getCollection(collectionName).createIndex(dbObject);
    }
    public void insertOne(Bson dbObject,String collectionName){
        mongoTemplate.getCollection(collectionName).createIndex(dbObject);
    }
    public void insertMany(List<Document> dbObject, String collectionName){
        mongoTemplate.getCollection(collectionName).insertMany(dbObject);
    }
    public void getAll(String collectionName){
        mongoTemplate.getCollection(collectionName).find();
    }


    /**
     *
     * @param collectionName collection   新版本待测试 TODO
     * @param field 字段
     * @param value 字段值
     * @return
     */
    public AggregateIterable<Document> getStatic(String collectionName, String field, String value){//例如abnormal，data.datas.abnormalType
        Aggregation aggregation =
                Aggregation.newAggregation(
                        Aggregation.unwind("data.datas"),
                        Aggregation.match(Criteria.where("_id").gt(0).lt(100000).and(field).is(value)),
                        Aggregation.group("_id").count().as("count")
                ).withOptions(newAggregationOptions().cursor(new Document()).build());


        //查询方法一
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, collectionName, Document.class);
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
        AggregateIterable<Document> documents = mongoTemplate.getCollection(collectionName).aggregate(pipeLine);
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            System.out.println("cursor"+iterator.next());

        }
        return documents;
    }
}
