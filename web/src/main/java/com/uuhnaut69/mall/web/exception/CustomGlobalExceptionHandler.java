package com.uuhnaut69.mall.web.exception;

import com.uuhnaut69.mall.core.exception.AuthorizeException;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Custom unAuth exception handler
     *
     * @param ex      {@link Exception}
     * @param request {@link WebRequest}
     * @return ErrorResponse
     */
    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorResponse> unAuth(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setContextPath(request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Custom bad request exception handler
     *
     * @param ex      {@link Exception}
     * @param request {@link WebRequest}
     * @return ErrorResponse
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setContextPath(request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom not found exception handler
     *
     * @param ex      {@link Exception}
     * @param request {@link WebRequest}
     * @return ErrorResponse
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> customNotFound(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setContextPath(request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Custom general exception
     *
     * @param ex      {@link Exception}
     * @param request {@link WebRequest}
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> customGeneralException(Exception ex, WebRequest request) {
        log.info(ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setContextPath(request.getContextPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
