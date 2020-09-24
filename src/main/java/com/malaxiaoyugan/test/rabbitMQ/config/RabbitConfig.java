package com.malaxiaoyugan.test.rabbitMQ.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: RabbitConfig
 * date: 2020/9/24 10:59
 * author: juquansheng
 * version: 1.0 <br>
 */
@Configuration
public class RabbitConfig {

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
    private Boolean  publisherConfirms;
    @Value("${rabbitmq.publisher-returns}")
    private Boolean  publisherReturns;

    /**
     * 设置amqp连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        // 设置连接超时时间
        connectionFactory.setConnectionTimeout(600000);
        connectionFactory.setCloseTimeout(600000);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        return connectionFactory;
    }

    /**
     * 使用connectionFactory初始化RabbitTemplate
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }



    //1.定义交换机
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("DIRECT_EXCHANGE");
    }

    @Bean
    public TopicExchange topicExchange(){

        return new TopicExchange("TOPIC_EXCHANGE");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("FANOUT_EXCHANGE");
    }

    @Bean
    public DirectExchange testExchange(){
        return new DirectExchange("TEST_EXCHANGE");
    }
    //2.定义队列
    @Bean
    public Queue firstQueue(){
        return new Queue("FIRST_QUEUE");
    }

    @Bean
    public Queue secondQueue(){
        return new Queue("SECOND_QUEUE");
    }

    @Bean
    public Queue thirdQueue(){
        return new Queue("THIRD_QUEUE");
    }

    @Bean
    public Queue fourthQueue(){
        return new Queue("FOURTH_QUEUE");
    }

    @Bean
    public Queue testQueue(){
        return new Queue("TEST_QUEUE");
    }
    //3.定义绑定关系
    @Bean
    public Binding bindFirst(@Qualifier("firstQueue") Queue queue,
                             @Qualifier("directExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("wuzz.test");
    }

    @Bean
    public Binding bindSecond(@Qualifier("secondQueue") Queue queue,
                              @Qualifier("topicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("*.wuzz.*");
    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue,
                             @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("fourthQueue") Queue queue,
                              @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindTest(@Qualifier("testQueue") Queue queue,
                             @Qualifier("testExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("test");
    }
}
