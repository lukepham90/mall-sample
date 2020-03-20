package com.uuhnaut69.mall.service.rating;

import com.uuhnaut69.mall.payload.response.ProductResponse;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface RatingProductService {

    /**
     * Rating a product
     *
     * @param productId {@link UUID}
     * @param userId    {@link UUID}
     * @param rating    Rating
     * @return int
     */
    Integer ratingProduct(UUID productId, UUID userId, int rating);

    /**
     * Get rating aggregation of product
     *
     * @param productResponse {@link ProductResponse}
     */
    void getRatingAggregationOfProduct(ProductResponse productResponse);

    /**
     * Get rating aggregation of products
     *
     * @param productResponses List {@link ProductResponse}
     */
    void getRatingAggregationOfProducts(List<ProductResponse> productResponses);

}
