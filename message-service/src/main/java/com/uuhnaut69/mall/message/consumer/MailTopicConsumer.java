package com.uuhnaut69.mall.message.consumer;

import com.uuhnaut69.mall.mail.service.MailService;
import com.uuhnaut69.mall.message.constant.RabbitMqConstants;
import com.uuhnaut69.mall.product.domain.model.User;
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
public class MailTopicConsumer {

    private final MailService mailService;

    public MailTopicConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = RabbitMqConstants.SEND_ACTIVATE_MAIL_TOPIC)
    private void mailTopicConsumer(User receivedObject) {
        if (receivedObject instanceof User) {
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