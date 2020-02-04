package com.uuhnaut69.mall.core.exception;

/**
 * @author uuhnaut
 * @project mall
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
