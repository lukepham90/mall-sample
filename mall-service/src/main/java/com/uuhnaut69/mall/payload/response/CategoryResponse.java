package com.uuhnaut69.mall.payload.response;

import lombok.Data;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CategoryResponse {

    private UUID id;

    private String categoryName;
}
