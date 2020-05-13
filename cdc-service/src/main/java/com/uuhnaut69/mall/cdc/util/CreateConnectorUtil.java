package com.uuhnaut69.mall.cdc.util;

import com.uuhnaut69.mall.cdc.config.CustomEmbeddedEngine;
import io.debezium.connector.postgresql.PostgresConnectorConfig;
import io.debezium.embedded.EmbeddedEngine;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * @author uuhnaut
 * @project mall
 * @date 5/13/20
 */
public final class CreateConnectorUtil {

    private CreateConnectorUtil() {
    }

    public static Properties createConnector(Timestamp fromCheckpointTime) {
        Properties properties = new Properties();
        properties.setProperty(EmbeddedEngine.CONNECTOR_CLASS.toString(), "io.debezium.connector.postgresql.PostgresConnector");
        properties.setProperty(EmbeddedEngine.OFFSET_STORAGE.toString(), "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        properties.setProperty(EmbeddedEngine.OFFSET_STORAGE_FILE_FILENAME.toString(), "");
        properties.setProperty(EmbeddedEngine.OFFSET_FLUSH_INTERVAL_MS.toString(), "60000");
        properties.setProperty(EmbeddedEngine.ENGINE_NAME.toString(), "product-postgres-connector");
        properties.setProperty(PostgresConnectorConfig.SERVER_NAME.toString(), "cdc-server");
        properties.setProperty(PostgresConnectorConfig.HOSTNAME.toString(), "localhost");
        properties.setProperty(PostgresConnectorConfig.PORT.toString(), "5432");
        properties.setProperty(PostgresConnectorConfig.USER.toString(), "postgres");
        properties.setProperty(PostgresConnectorConfig.PASSWORD.toString(), "postgres");
        properties.setProperty(PostgresConnectorConfig.DATABASE_NAME.toString(), "postgres");
        properties.setProperty(PostgresConnectorConfig.TABLE_WHITELIST.toString(),
                "public.product,public.user_product,public.user_tag,public.users,public.product_tag,public.tag");
        if (fromCheckpointTime != null) {
            properties.setProperty(CustomEmbeddedEngine.CHECK_POINT_MODE, fromCheckpointTime.toString());
        }
        return properties;
    }

}
