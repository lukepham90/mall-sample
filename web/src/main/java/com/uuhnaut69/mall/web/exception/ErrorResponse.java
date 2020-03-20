package com.uuhnaut69.mall.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"status", "error", "timestamp"})
public class ErrorResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String contextPath;
}
