package com.malaxiaoyugan.test.mongoDB.service;

import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
