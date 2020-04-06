package com.uuhnaut69.mall.payload.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class ProductRequest {

    private String productName;

    private String description;

    private Integer stocks;

    private String imageUrls;

    private String thumbNail;

    private BigDecimal originalPrice;

    private BigDecimal discountPrice;

    private Set<UUID> uuidTags;

    private Boolean trending;

}
