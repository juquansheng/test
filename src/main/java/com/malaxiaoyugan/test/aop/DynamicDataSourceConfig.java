package com.malaxiaoyugan.test.aop;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 */
@Configuration
@Component
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.yuuki-forum")
    public DataSource forumDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.yuuki-admin")
    public DataSource adminDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource forumDataSource, DataSource adminDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put("forum", forumDataSource);
        targetDataSources.put("admin", adminDataSource);
        return new DynamicDataSource(forumDataSource, targetDataSources);
    }
}
