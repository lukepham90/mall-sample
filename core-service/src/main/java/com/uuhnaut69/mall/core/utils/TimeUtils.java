package com.uuhnaut69.mall.core.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class TimeUtils {

    private TimeUtils() {
    }

    /**
     * Get current date time
     *
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
