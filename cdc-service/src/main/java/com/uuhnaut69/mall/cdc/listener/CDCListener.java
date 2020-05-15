package com.uuhnaut69.mall.cdc.listener;

import com.uuhnaut69.mall.cdc.constant.CDCTableConstant;
import com.uuhnaut69.mall.cdc.util.CreateConnectorUtil;
import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.service.index.*;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
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

    private final Executor executor = Executors.newSingleThreadExecutor();

    private DebeziumEngine<SourceRecord> engine;

    private final ProductEsService productEsService;

    private final ProductTagEsService productTagEsService;

    private final UserEsService userEsService;

    private final UserProductEsService userProductEsService;

    private final UserTagEsService userTagEsService;

    private final TagEsService tagEsService;

    public CDCListener(ProductEsService productEsService, ProductTagEsService productTagEsService, UserEsService userEsService,
                       UserProductEsService userProductEsService, UserTagEsService userTagEsService, TagEsService tagEsService) {
        this.productEsService = productEsService;
        this.productTagEsService = productTagEsService;
        this.userEsService = userEsService;
        this.userProductEsService = userProductEsService;
        this.userTagEsService = userTagEsService;
        this.tagEsService = tagEsService;
    }

    @PostConstruct
    public void start() {
        this.engine = DebeziumEngine.create(Connect.class).using(CreateConnectorUtil.createConnector()).notifying(this::handleEvent).build();
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
            log.info("{}", sourceRecordValue);

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
                        log.info("Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.PRODUCT_TAG_TABLE:
                        messageBefore = getMessage(sourceRecordValue, BEFORE);
                        this.productTagEsService.handleCdcEvent(message, messageBefore, operation);
                        log.info("Product Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_TABLE:
                        this.userEsService.handleCdcEvent(message, operation);
                        log.info("User Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_PRODUCT_TABLE:
                        this.userProductEsService.handleCdcEvent(message, operation);
                        log.info("User Product Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.USER_TAG_TABLE:
                        messageBefore = getMessage(sourceRecordValue, BEFORE);
                        this.userTagEsService.handleCdcEvent(message, messageBefore, operation);
                        log.info("User Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());

                        break;
                    case CDCTableConstant.TAG_TABLE:
                        this.tagEsService.handleCdcEvent(message, operation);
                        log.info("Tag Data Changed: {} with Operation: {}", message, Objects.requireNonNull(operation).name());
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
            message = struct.schema().fields().stream().map(Field::name)
                    .filter(fieldName -> struct.get(fieldName) != null)
                    .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                    .collect(toMap(Pair::getKey, Pair::getValue));
        }
        return message;
    }
}
