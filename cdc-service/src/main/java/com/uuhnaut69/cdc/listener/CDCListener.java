package com.uuhnaut69.cdc.listener;

import com.uuhnaut69.cdc.constant.CDCTableConstant;
import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.service.index.*;
import com.uuhnaut69.search.service.index.impl.ProductCategoryService;
import com.uuhnaut69.search.service.index.impl.ProductTagService;
import io.debezium.connector.postgresql.PostgresConnectorConfig;
import io.debezium.embedded.Connect;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.DebeziumEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static java.util.stream.Collectors.toMap;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Component
public class CDCListener {

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

  private final Executor executor = Executors.newSingleThreadExecutor();

  private DebeziumEngine<SourceRecord> engine;

  private final ProductEsService productEsService;

  private final ProductTagService productTagService;

  private final UserEsService userEsService;

  private final UserProductEsService userProductEsService;

  private final UserTagEsService userTagEsService;

  private final TagEsService tagEsService;

  private final ProductCategoryService productCategoryService;

  private final CategoryEsService categoryEsService;

  public CDCListener(
      ProductEsService productEsService,
      ProductTagService productTagService,
      UserEsService userEsService,
      UserProductEsService userProductEsService,
      UserTagEsService userTagEsService,
      TagEsService tagEsService,
      ProductCategoryService productCategoryService,
      CategoryEsService categoryEsService) {
    this.productEsService = productEsService;
    this.productTagService = productTagService;
    this.userEsService = userEsService;
    this.userProductEsService = userProductEsService;
    this.userTagEsService = userTagEsService;
    this.tagEsService = tagEsService;
    this.productCategoryService = productCategoryService;
    this.categoryEsService = categoryEsService;
  }

  @PostConstruct
  public void start() {
    this.engine =
        DebeziumEngine.create(Connect.class)
            .using(createConnector())
            .notifying(this::handleEvent)
            .build();
    this.executor.execute(engine);
  }

  @PreDestroy
  public void stop() throws IOException {
    if (this.engine != null) {
      this.engine.close();
    }
  }

  private void handleEvent(SourceRecord sourceRecord) {
    Struct sourceRecordValue = (Struct) sourceRecord.value();

    if (sourceRecordValue != null) {

      Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));
      log.debug("{}", sourceRecordValue);

      if (operation != Operation.READ) {

        String record = AFTER; // For Update & Insert operations.
        if (operation == Operation.DELETE) {
          record = BEFORE; // For Delete operations.
        }

        // Build a map with all row data received.
        Map<String, Object> message = getMessage(sourceRecordValue, record);

        Struct extract = (Struct) sourceRecordValue.get(SOURCE);
        String tableChange = extract.get("table").toString();

        Map<String, Object> messageBefore;

        switch (tableChange) {
          case CDCTableConstant.PRODUCT_TABLE:
            this.productEsService.handleCdcEvent(message, operation);
            log.debug(
                "Product Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());

            break;
          case CDCTableConstant.PRODUCT_TAG_TABLE:
            messageBefore = getMessage(sourceRecordValue, BEFORE);
            this.productTagService.handleCdcEvent(message, messageBefore, operation);
            log.debug(
                "Product Tag Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());

            break;
          case CDCTableConstant.USER_TABLE:
            this.userEsService.handleCdcEvent(message, operation);
            log.debug(
                "User Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());

            break;
          case CDCTableConstant.USER_PRODUCT_TABLE:
            this.userProductEsService.handleCdcEvent(message, operation);
            log.debug(
                "User Product Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());

            break;
          case CDCTableConstant.USER_TAG_TABLE:
            messageBefore = getMessage(sourceRecordValue, BEFORE);
            this.userTagEsService.handleCdcEvent(message, messageBefore, operation);
            log.debug(
                "User Tag Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());

            break;
          case CDCTableConstant.TAG_TABLE:
            this.tagEsService.handleCdcEvent(message, operation);
            log.debug(
                "Tag Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());
            break;
          case CDCTableConstant.CATEGORY_TABLE:
            this.categoryEsService.handleCdcEvent(message, operation);
            log.debug(
                "Category Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());
            break;
          case CDCTableConstant.PRODUCT_CATEGORY_TABLE:
            messageBefore = getMessage(sourceRecordValue, BEFORE);
            this.productCategoryService.handleCdcEvent(message, messageBefore, operation);
            log.debug(
                "Product Category Data Changed: {} with Operation: {}",
                message,
                Objects.requireNonNull(operation).name());
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + tableChange);
        }
      }
    }
  }

  private Map<String, Object> getMessage(Struct sourceRecordValue, String status) {
    Struct struct = (Struct) sourceRecordValue.get(status);
    Map<String, Object> message = new HashMap<>();
    if (struct != null) {
      message =
          struct.schema().fields().stream()
              .map(Field::name)
              .filter(fieldName -> struct.get(fieldName) != null)
              .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
              .collect(toMap(Pair::getKey, Pair::getValue));
    }
    return message;
  }

  private Properties createConnector() {
    Properties properties = new Properties();
    properties.setProperty(
        EmbeddedEngine.CONNECTOR_CLASS.toString(),
        "io.debezium.connector.postgresql.PostgresConnector");
    properties.setProperty(
        EmbeddedEngine.OFFSET_STORAGE.toString(),
        "org.apache.kafka.connect.storage.FileOffsetBackingStore");
    properties.setProperty(EmbeddedEngine.OFFSET_STORAGE_FILE_FILENAME.toString(), offsetDir);
    properties.setProperty(EmbeddedEngine.OFFSET_FLUSH_INTERVAL_MS.toString(), "60000");
    properties.setProperty(EmbeddedEngine.ENGINE_NAME.toString(), "mall-connector");
    properties.setProperty(PostgresConnectorConfig.SERVER_NAME.toString(), "cdc-server");
    properties.setProperty(PostgresConnectorConfig.HOSTNAME.toString(), dbHost);
    properties.setProperty(PostgresConnectorConfig.PORT.toString(), "5432");
    properties.setProperty(PostgresConnectorConfig.USER.toString(), dbUser);
    properties.setProperty(PostgresConnectorConfig.PASSWORD.toString(), dbPassword);
    properties.setProperty(PostgresConnectorConfig.DATABASE_NAME.toString(), dbName);
    properties.setProperty(
        PostgresConnectorConfig.TABLE_WHITELIST.toString(),
        "public.product,public.user_product,public.user_tag,public.users,public.product_tag,public.tag,public.product_category,public.category");
    return properties;
  }
}
