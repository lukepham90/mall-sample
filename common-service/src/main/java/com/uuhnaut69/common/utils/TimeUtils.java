package com.uuhnaut69.common.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author uuhnaut
 * @project mall
 */
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
