package com.uuhnaut69.mall.search.listener;

import com.uuhnaut69.mall.search.constant.CDCTableConstants;
import com.uuhnaut69.mall.search.service.*;
import com.uuhnaut69.mall.search.utils.Operation;
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
     * @throws Exception
     */
    private void handleEvent(SourceRecord sourceRecord) throws Exception {
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        if (sourceRecordValue != null) {

            Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));
            log.info("{}", sourceRecordValue);

            // Only if this is a transactional operation.
            if (operation != Operation.READ) {

                Map<String, Object> message;
                String record = AFTER; // For Update & Insert operations.

                if (operation == Operation.DELETE) {
                    record = BEFORE; // For Delete operations.
                }

                // Build a map with all row data received.
                Struct struct = (Struct) sourceRecordValue.get(record);
                message = struct.schema().fields().stream().map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));

                Struct extract = (Struct) sourceRecordValue.get(SOURCE);
                String tableChange = extract.get("table").toString();

                // Call the service to handle the data change.
                switch (tableChange) {
                    case CDCTableConstants.BRAND_TABLE:
                        this.brandEsService.maintainReadModel(message, operation);
                        log.info("Brand Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstants.CATALOG_TABLE:
                        this.catalogEsService.maintainReadModel(message, operation);
                        log.info("Catalog Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstants.PRODUCT_TABLE:
                        this.productEsService.maintainReadModel(message, operation);
                        log.info("Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstants.PRODUCT_TAG_TABLE: {
                        // Build a map with all row data before received.
                        Struct structBefore = (Struct) sourceRecordValue.get(BEFORE);
                        Map<String, Object> messageBefore = new HashMap<>();
                        if (structBefore != null) {
                            messageBefore = structBefore.schema().fields().stream().map(Field::name)
                                    .filter(fieldName -> struct.get(fieldName) != null)
                                    .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                                    .collect(toMap(Pair::getKey, Pair::getValue));
                        }
                        this.productTagEsService.maintainReadModel(message, messageBefore, operation);
                        log.info("Product Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    }
                    case CDCTableConstants.USER_TABLE:
                        this.userEsService.maintainReadModel(message, operation);
                        log.info("User Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstants.USER_PRODUCT_TABLE:
                        this.userProductEsService.maintainReadModel(message, operation);
                        log.info("User Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstants.USER_TAG_TABLE: {
                        // Build a map with all row data before received.
                        Struct structBefore = (Struct) sourceRecordValue.get(BEFORE);
                        Map<String, Object> messageBefore = new HashMap<>();
                        if (structBefore != null) {
                            messageBefore = structBefore.schema().fields().stream().map(Field::name)
                                    .filter(fieldName -> struct.get(fieldName) != null)
                                    .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                                    .collect(toMap(Pair::getKey, Pair::getValue));
                        }
                        this.userTagEsService.maintainReadModel(message, messageBefore, operation);
                        log.info("User Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    }
                    case CDCTableConstants.TAG_TABLE:
                        this.tagEsService.maintainReadModel(message, operation);
                        log.info("Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());
                        break;
                }

            }
        }
    }

}
