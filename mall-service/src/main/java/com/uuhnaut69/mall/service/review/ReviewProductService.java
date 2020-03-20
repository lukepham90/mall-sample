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
     * @param pageable  {@link Pageable}
     * @param productId {@link UUID}
     * @return List {@link ReviewResponse}
     */
    List<ReviewResponse> findReviewsOfProduct(Pageable pageable, UUID productId);

    /**
     * Find replies of a review
     *
     * @param pageable  {@link Pageable}
     * @param productId {@link UUID}
     * @param reviewId  {@link UUID}
     * @return List {@link ReviewResponse}
     */
    List<ReviewResponse> findReplyReviewsOfProduct(Pageable pageable, UUID productId, UUID reviewId);

    /**
     * Create review
     *
     * @param productId     {@link UUID}
     * @param userId        {@link UUID}
     * @param reviewRequest {@link ReviewRequest}
     * @return ReviewResponse
     */
    ReviewResponse create(UUID productId, UUID userId, ReviewRequest reviewRequest);

    /**
     * Update review
     *
     * @param productId     {@link UUID}
     * @param reviewId      {@link UUID}
     * @param userId        {@link UUID}
     * @param reviewRequest {@link ReviewRequest}
     * @return ReviewResponse
     */
    ReviewResponse update(UUID productId, UUID reviewId, UUID userId, ReviewRequest reviewRequest);

    /**
     * Count review amount of a product
     *
     * @param productResponse {@link ProductResponse}
     */
    void countReviewsOfAProduct(ProductResponse productResponse);

    /**
     * Count review amount of list product
     *
     * @param productResponses List {@link ProductResponse}
     * @return List {@link ProductResponse}
     */
    void countReviewsOfListProduct(List<ProductResponse> productResponses);

}
