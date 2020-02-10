package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CatalogRequest {
    @NotBlank
    private String catalogName;
}