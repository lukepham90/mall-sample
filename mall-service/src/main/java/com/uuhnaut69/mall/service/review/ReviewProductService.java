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

    List<ReviewResponse> findReviewsOfProduct(Pageable pageable, UUID productId);

    List<ReviewResponse> findReplyReviewsOfProduct(Pageable pageable, UUID productId, UUID reviewId);

    ReviewResponse create(UUID productId, UUID userId, ReviewRequest reviewRequest);

    ReviewResponse update(UUID productId, UUID reviewId, UUID userId, ReviewRequest reviewRequest);

    void countReviewsOfAProduct(ProductResponse productResponse);

    void countReviewsOfListProduct(List<ProductResponse> productResponses);

}
