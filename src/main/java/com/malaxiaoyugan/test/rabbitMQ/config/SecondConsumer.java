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
@RabbitListener(queues = "SECOND_QUEUE")
public class SecondConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("second Queue received msg : " + msg);
    }
}
