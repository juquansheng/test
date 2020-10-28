package com.malaxiaoyugan.test;

import com.malaxiaoyugan.test.aop.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Import({DynamicDataSourceConfig.class})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除自动配置
@EnableScheduling //开启定时任务
@ComponentScan(basePackages={"com.malaxiaoyugan.test.pay.mq","com.malaxiaoyugan.test.controller","com.malaxiaoyugan.test.rabbitMQ","com.malaxiaoyugan.test.mongoDB.config"})
@MapperScan("com.malaxiaoyugan.test.dao")
public class TestApplication {

    public static void main(String[] args) {
        //System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(TestApplication.class, args);
    }

}
