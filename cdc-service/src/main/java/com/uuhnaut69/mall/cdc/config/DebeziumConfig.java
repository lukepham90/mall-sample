package com.uuhnaut69.mall.cdc.config;

import io.debezium.connector.postgresql.PostgresConnectorConfig;
import io.debezium.embedded.EmbeddedEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uuhnaut
 * @project mall
 */
@Configuration
public class DebeziumConfig {

    @Value("${offset.directory.path}")
    private String offsetDir;

    @Value("${database.host}")
    private String dbHost;

    @Value("${database.user}")
    private String dbUser;

    @Value("${database.password}")
    private String dbPassword;

    @Value("${database.name}")
    private String dbName;

    /**
     * Configuration database connector.
     *
     * @return Configuration.
     */
    @Bean
    public io.debezium.config.Configuration studentConnector() {
        return io.debezium.config.Configuration.create()
                .with(EmbeddedEngine.CONNECTOR_CLASS, "io.debezium.connector.postgresql.PostgresConnector")
                .with(EmbeddedEngine.OFFSET_STORAGE, "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with(EmbeddedEngine.OFFSET_STORAGE_FILE_FILENAME, offsetDir)
                .with(EmbeddedEngine.OFFSET_FLUSH_INTERVAL_MS, 60000)
                .with(EmbeddedEngine.ENGINE_NAME, "product-postgres-connector")
                .with(PostgresConnectorConfig.SERVER_NAME, dbHost + "-" + "cdc")
                .with(PostgresConnectorConfig.HOSTNAME, dbHost)
                .with(PostgresConnectorConfig.PORT, 5432)
                .with(PostgresConnectorConfig.USER, dbUser)
                .with(PostgresConnectorConfig.PASSWORD, dbPassword)
                .with(PostgresConnectorConfig.DATABASE_NAME, dbName)
                .with(PostgresConnectorConfig.TABLE_WHITELIST,
                        "public.brand,public.product,public.catalog,public.user_product,public.user_tag,public.users,public.product_tag,public.tag")
                .build();
    }

}
