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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ErrorResponse> unAuth(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = getErrorMessages(ex, request, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = getErrorMessages(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> customNotFound(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = getErrorMessages(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> customGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = getErrorMessages(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ErrorResponse getErrorMessages(Exception ex, HttpServletRequest request, HttpStatus unauthorized) {
        log.info(ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(unauthorized.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setRequestUrl(request.getRequestURL().toString());
        StringBuilder errorLog = new StringBuilder();
        errorLog.append("[").append(request.getMethod()).append("]");
        errorLog.append(request.getRequestURL().toString());
        errorLog.append(". Caused by ").append(ex.getMessage());
        log.error(errorLog.toString());
        return errorResponse;
    }
}
