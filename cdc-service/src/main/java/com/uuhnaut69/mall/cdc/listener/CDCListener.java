package com.uuhnaut69.mall.cdc.listener;

import com.uuhnaut69.mall.cdc.constant.CDCTableConstant;
import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.service.index.*;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    /**
     * Single thread pool which will run the Debezium engine asynchronously.
     */
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * The Debezium engine which needs to be loaded with the configurations, Started
     * and Stopped - for the CDC to work.
     */
    private final EmbeddedEngine engine;

    private final BrandEsService brandEsService;

    private final CatalogEsService catalogEsService;

    private final ProductEsService productEsService;

    private final ProductTagEsService productTagEsService;

    private final UserEsService userEsService;

    private final UserProductEsService userProductEsService;

    private final UserTagEsService userTagEsService;

    private final TagEsService tagEsService;

    public CDCListener(Configuration connector, BrandEsService brandEsService, CatalogEsService catalogEsService,
                       ProductEsService productEsService, ProductTagEsService productTagEsService, UserEsService userEsService,
                       UserProductEsService userProductEsService, UserTagEsService userTagEsService, TagEsService tagEsService) {
        this.engine = EmbeddedEngine.create().using(connector).notifying(t -> {
            try {
                handleEvent(t);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }).build();
        this.brandEsService = brandEsService;
        this.catalogEsService = catalogEsService;
        this.productEsService = productEsService;
        this.productTagEsService = productTagEsService;
        this.userEsService = userEsService;
        this.userProductEsService = userProductEsService;
        this.userTagEsService = userTagEsService;
        this.tagEsService = tagEsService;
    }

    /**
     * The method is called after the Debezium engine is initialized and started
     * asynchronously using the Executor.
     */
    @PostConstruct
    private void start() {
        this.executor.execute(engine);
    }

    /**
     * This method is called when the container is being destroyed. This stops the
     * debezium, merging the Executor.
     */
    @PreDestroy
    private void stop() {
        if (this.engine != null) {
            this.engine.stop();
        }
    }

    /**
     * This method is invoked when a transactional action is performed on any of the
     * tables that were configured.
     *
     * @param sourceRecord
     */
    private void handleEvent(SourceRecord sourceRecord) {
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        if (sourceRecordValue != null) {

            Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));
            log.info("{}", sourceRecordValue);

            // Only if this is a transactional operation.
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

                // Call the service to handle the data change.
                switch (tableChange) {
                    case CDCTableConstant.BRAND_TABLE:
                        this.brandEsService.maintainReadModel(message, operation);
                        log.info("Brand Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.CATALOG_TABLE:
                        this.catalogEsService.maintainReadModel(message, operation);
                        log.info("Catalog Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.PRODUCT_TABLE:
                        this.productEsService.maintainReadModel(message, operation);
                        log.info("Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.PRODUCT_TAG_TABLE:
                        messageBefore = getMessage(sourceRecordValue, BEFORE);
                        this.productTagEsService.maintainReadModel(message, messageBefore, operation);
                        log.info("Product Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_TABLE:
                        this.userEsService.maintainReadModel(message, operation);
                        log.info("User Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_PRODUCT_TABLE:
                        this.userProductEsService.maintainReadModel(message, operation);
                        log.info("User Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_TAG_TABLE:
                        messageBefore = getMessage(sourceRecordValue, BEFORE);
                        this.userTagEsService.maintainReadModel(message, messageBefore, operation);
                        log.info("User Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.TAG_TABLE:
                        this.tagEsService.maintainReadModel(message, operation);
                        log.info("Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tableChange);
                }

            }
        }
    }

    /**
     * Get message event from postgres
     *
     * @param sourceRecordValue
     * @param status
     * @return Map
     */
    private Map<String, Object> getMessage(Struct sourceRecordValue, String status) {
        Struct struct = (Struct) sourceRecordValue.get(status);
        Map<String, Object> message = new HashMap<>();
        if (struct != null) {
            message = struct.schema().fields().stream().map(Field::name)
                    .filter(fieldName -> struct.get(fieldName) != null)
                    .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                    .collect(toMap(Pair::getKey, Pair::getValue));
        }
        return message;
    }
}
