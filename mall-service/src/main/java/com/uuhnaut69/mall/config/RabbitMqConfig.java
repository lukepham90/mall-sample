package com.uuhnaut69.mall.config;

import com.uuhnaut69.mall.constant.RabbitMqConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    Queue queue() {
        return new Queue(RabbitMqConstant.SEND_ACTIVATE_MAIL_TOPIC, false);
    }

}