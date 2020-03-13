package com.uuhnaut69.mall.service.review;

import com.uuhnaut69.mall.payload.request.ReviewRequest;
import com.uuhnaut69.mall.payload.response.ProductResponse;
import com.uuhnaut69.mall.payload.response.ReviewResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ReviewProductService {

    /**
     * Find reviews of product (Load list 1st at hierarchy)
     *
     * @param pageable
     * @param productId
     * @return List {@link ReviewResponse}
     */
    List<ReviewResponse> findReviewsOfProduct(Pageable pageable, UUID productId);

    /**
     * Find replies of a review
     *
     * @param pageable
     * @param productId
     * @param reviewId
     * @return List {@link ReviewResponse}
     */
    List<ReviewResponse> findReplyReviewsOfProduct(Pageable pageable, UUID productId, UUID reviewId);

    /**
     * Create review
     *
     * @param productId
     * @param userId
     * @param reviewRequest
     * @return ReviewResponse
     * @throws Exception
     */
    ReviewResponse create(UUID productId, UUID userId, ReviewRequest reviewRequest) throws Exception;

    /**
     * Update review
     *
     * @param productId
     * @param reviewId
     * @param userId
     * @param reviewRequest
     * @return ReviewResponse
     */
    ReviewResponse update(UUID productId, UUID reviewId, UUID userId, ReviewRequest reviewRequest);

    /**
     * Count review amount of a product
     *
     * @param productResponse
     */
    void countReviewsOfAProduct(ProductResponse productResponse);

    /**
     * Count review amount of list product
     *
     * @param productResponses
     * @return List {@link ProductResponse}
     */
    void countReviewsOfListProduct(List<ProductResponse> productResponses);

}
