package com.uuhnaut69.mall.kafka;

import com.uuhnaut69.mall.constant.KafkaConstant;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.service.mail.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Component
public class MailingConsumer {

    private final MailService mailService;

    public MailingConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = KafkaConstant.MAILING_QUEUE_ACTIVE_ACCOUNT, groupId = KafkaConstant.GROUP_ID)
    public void receive(Object receivedObject) throws Exception {

        @SuppressWarnings("rawtypes")
        ConsumerRecord consumerRecord = (ConsumerRecord) receivedObject;

        if (consumerRecord.value() instanceof User && consumerRecord.value() != null) {
            mailService.sendMail((User) consumerRecord.value());
        } else {
            log.warn("Unable to handle consumer record: " + consumerRecord);
        }

    }
}
