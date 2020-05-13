package com.uuhnaut69.mall.service.rating;

import com.uuhnaut69.mall.payload.response.ProductResponse;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface RatingProductService {

    Integer ratingProduct(UUID productId, UUID userId, int rating);

    void getRatingAggregationOfProduct(ProductResponse productResponse);

    void getRatingAggregationOfProducts(List<ProductResponse> productResponses);

}
