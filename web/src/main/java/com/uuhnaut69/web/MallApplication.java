package com.uuhnaut69.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author uuhnaut
 * @project mall
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.uuhnaut69.*"})
@EntityScan(basePackages = {"com.uuhnaut69.*"})
@EnableJpaRepositories(basePackages = {"com.uuhnaut69.*"})
@EnableElasticsearchRepositories(basePackages = {"com.uuhnaut69.*"})
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
