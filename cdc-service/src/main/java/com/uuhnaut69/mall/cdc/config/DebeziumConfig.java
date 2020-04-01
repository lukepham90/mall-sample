package com.uuhnaut69.mall.cdc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class DebeziumConfig {

    @Value("${offsetDir}")
    private String offsetDir;

    @Value("${dbHost}")
    private String dbHost;

    @Value("${dbUser}")
    private String dbUser;

    @Value("${dbPassword}")
    private String dbPassword;

    @Value("${dbName}")
    private String dbName;

    /**
     * Configuration database connector.
     *
     * @return Configuration.
     */
    @Bean
    public io.debezium.config.Configuration studentConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetDir).with("offset.flush.interval.ms", 60000)
                .with("name", "product-postgres-connector").with("database.server.name", dbHost + "-" + "cdc")
                .with("database.hostname", dbHost).with("database.port", 5432).with("database.user", dbUser)
                .with("database.password", dbPassword).with("database.dbname", dbName)
                .with("table.whitelist",
                        "public.brand,public.product,public.catalog,public.user_product,public.user_tag,public.users,public.product_tag,public.tag")
                .build();
    }

}
