package com.uuhnaut69.common.exception;

/**
 * @author uuhnaut
 * @project mall
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
