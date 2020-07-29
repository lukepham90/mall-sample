package com.uuhnaut69.core.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CategoryRequest {

    @NotBlank
    private String categoryName;
}
