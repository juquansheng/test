package com.malaxiaoyugan.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: Producer
 * date: 2020/9/23 17:14
 * author: juquansheng
 * version: 1.0 <br>
 */
@Component
public class Producer {
    private final static String EXCHANGE_NAME = "TEST_EXCHANGE";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public  void send(){
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE","wuzz.test","DIRECT_EXCHANGE message");
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","hangzhou.wuzz.test","TOPIC_EXCHANGE hangzhou message");
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","wenzhou.wuzz.test","TOPIC_EXCHANGE wenzhou message");
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE","","FANOUT_EXCHANGE message");

    }


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 连接IP
        factory.setHost("127.0.0.1");
        // 连接端口
        factory.setPort(5672);
        // 虚拟机
        factory.setVirtualHost("/");
        // 用户
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setConnectionTimeout(600000);

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 发送消息
        String msg = "Hello world!!!!!!!!!!";

        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish(EXCHANGE_NAME, "test", null, msg.getBytes());

        channel.close();
        conn.close();
    }
}
