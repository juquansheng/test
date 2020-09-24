package com.malaxiaoyugan.test.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RabbitMQService
 * date: 2020/9/24 14:17
 * author: juquansheng
 * version: 1.0 <br>
 */
@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(JSONObject jsonObject){
        rabbitTemplate.convertAndSend("TEST_EXCHANGE","test",jsonObject.toJSONString());
//        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","hangzhou.wuzz.test","TOPIC_EXCHANGE hangzhou message");
//        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","wenzhou.wuzz.test","TOPIC_EXCHANGE wenzhou message");
//        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE","","FANOUT_EXCHANGE message");

    }

    public MessageConverter get(String queueName){
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        return messageConverter;

    }

}
