package com.malaxiaoyugan.test.rabbitMQ.config;

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
@RabbitListener(queues = "FIRST_QUEUE")
public class FirstConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("First Queue received msg : " + msg);
    }
}
