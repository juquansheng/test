package com.malaxiaoyugan.test.mongoDB.utils;

import com.malaxiaoyugan.test.mongoDB.config.ApplicationMongoDBConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * description: MongoUtil
 * date: 2020/10/27 9:56
 * author: juquansheng
 * version: 1.0 <br>
 */
@Slf4j
@Component
public class MongoDBUtil {
    /**
     * 因为Mongodb允许存在多个同名的用户存在，但同名的用户认证db必须不能相同
     */
    public static String authenticationDatabase;
    /**
     * 连接到数据库
     */
    public static String db;
    public static String username;
    public static String password;
    public static String ip;
    public static Integer port;

    @Autowired
    private ApplicationMongoDBConfig applicationMongoDBConfig;

    @PostConstruct
    public void init(){
        authenticationDatabase = applicationMongoDBConfig.getAuthenticationDatabase();
        db = applicationMongoDBConfig.getDb();
        username = applicationMongoDBConfig.getUsername();
        password = applicationMongoDBConfig.getPassword();
        ip = applicationMongoDBConfig.getHosts().get(0);
        port = applicationMongoDBConfig.getPorts().get(0);
        log.info("MongoUtil工具类初始参数---------------------------authenticationDatabase: {} ,db{},username{},password{},ip{},port{}",
                authenticationDatabase,db,username,password,ip,port);
    }

    //不通过认证获取连接数据库对象
    public static MongoDatabase getConnect(){
        //连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient(ip, port);
        //连接到数据库
        //返回连接数据库对象
        return mongoClient.getDatabase(db);
    }


    //需要密码认证方式连接
    public static MongoDatabase getConnectWithCredential(){

        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(ip, port);
        adds.add(serverAddress);
        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, authenticationDatabase, password.toCharArray());
        credentials.add(mongoCredential);
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);
        //连接到数据库
        //返回连接数据库对象
        return mongoClient.getDatabase(db);
    }


}
