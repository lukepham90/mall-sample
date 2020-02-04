package com.uuhnaut69.mall.message.config;

import com.uuhnaut69.mall.message.constant.RabbitMqConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author uuhnaut
 * @project mall
 */
public class RabbitMqConfig {

    @Bean
    Queue queue() {
        return new Queue(RabbitMqConstants.SEND_ACTIVATE_MAIL_TOPIC, false);
    }
}
