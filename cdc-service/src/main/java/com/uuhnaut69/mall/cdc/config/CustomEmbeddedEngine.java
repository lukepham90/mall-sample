package com.uuhnaut69.mall.cdc.config;

/**
 * @author uuhnaut
 * @project mall
 * @date 5/13/20
 */
public final class CustomEmbeddedEngine {

    private CustomEmbeddedEngine() {
    }

    /*
        Running debezium mode
     */
    public static final String CHECK_POINT_MODE = "from.checkpoint.time";
}
