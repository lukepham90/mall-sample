package com.uuhnaut69.mall.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author uuhnaut
 * @project mall
 */
@ComponentScan(basePackages = {"com.uuhnaut69.mall.*"})
@EntityScan(basePackages = {"com.uuhnaut69.mall.*"})
@EnableJpaRepositories(basePackages = {"com.uuhnaut69.mall.*"})
@EnableElasticsearchRepositories(basePackages = {"com.uuhnaut69.mall.search.*"})
@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }

}