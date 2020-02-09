package com.uuhnaut69.mall.message.consumer;

import com.uuhnaut69.mall.constant.RabbitMqConstant;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.service.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Component
@EnableRabbit
@RequiredArgsConstructor
public class MailTopicConsumer {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMqConstant.SEND_ACTIVATE_MAIL_TOPIC)
    private void mailTopicConsumer(User receivedObject) {
        if (receivedObject != null) {
            try {
                mailService.sendMail(receivedObject);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.warn("Unable to handle consumer record {} " + receivedObject);
        }
    }
}