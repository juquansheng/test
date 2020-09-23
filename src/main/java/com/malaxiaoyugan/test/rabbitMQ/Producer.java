package com.malaxiaoyugan.test.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * description: Producer
 * date: 2020/9/23 17:14
 * author: juquansheng
 * version: 1.0 <br>
 */
public class Producer {
    private final static String EXCHANGE_NAME = "TEST_EXCHANGE";
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private int port;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${rabbitmq.publisher-confirms}")
    private int publisherConfirms;
    @Value("${rabbitmq.publisher-returns}")
    private int publisherReturns;

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
