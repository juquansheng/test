package com.malaxiaoyugan.test.rabbitMQ.consumer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * description: FirstConsumer
 * date: 2020/9/24 11:03
 * author: juquansheng
 * version: 1.0 <br>
 */
@Configuration
@RabbitListener(queues = "TEST_QUEUE")
public class TestConsumer {
    @RabbitHandler
    public void process(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String o = jsonObject.getString("1");
        System.out.println("1 : " + o);

    }
}
