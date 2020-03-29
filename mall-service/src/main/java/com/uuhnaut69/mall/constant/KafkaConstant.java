package com.uuhnaut69.mall.constant;

/**
 * @author uuhnaut
 * @project mall
 */
public final class KafkaConstant {

    public static final String KAFKA_BROKER = "localhost:9092";
    public static final String GROUP_ID = "kafka-sandbox";
    // Kafka topics declare
    public static final String MAILING_QUEUE_ACTIVE_ACCOUNT = "mail_to_active_account";

    private KafkaConstant() {

    }
}
