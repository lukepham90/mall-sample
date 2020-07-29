package com.uuhnaut69.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductResponse {

    private UUID id;

    private String productName;

    private String description;

    private Integer stocks;

    private String imageUrls;

    private String thumbNail;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Set<TagResponse> tags;

    private Set<CategoryResponse> categories;

    private boolean trending;
}
