package com.uuhnaut69.mall.service.review.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.ReviewProduct;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.mapper.ReviewProductMapper;
import com.uuhnaut69.mall.payload.request.ReviewRequest;
import com.uuhnaut69.mall.payload.response.ProductResponse;
import com.uuhnaut69.mall.payload.response.ReviewResponse;
import com.uuhnaut69.mall.repository.review.ReviewProductRepository;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.review.ReviewProductService;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewProductServiceImpl implements ReviewProductService {

    private final ReviewProductRepository reviewProductRepository;

    private final UserService userService;

    private final ProductService productService;

    private final ReviewProductMapper reviewProductMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findReviewsOfProduct(Pageable pageable, UUID productId) {
        List<ReviewResponse> reviewResponses = reviewProductMapper.toListReviewResponse(reviewProductRepository.findAllByProductIdAndParentReviewProductId(pageable, productId, null).getContent());
        countRepliesOfReview(reviewResponses);
        return reviewResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findReplyReviewsOfProduct(Pageable pageable, UUID productId, UUID reviewId) {
        List<ReviewResponse> reviewResponses = reviewProductMapper.toListReviewResponse(reviewProductRepository.findAllByProductIdAndParentReviewProductId(pageable, productId, reviewId).getContent());
        countRepliesOfReview(reviewResponses);
        return reviewResponses;
    }

    @Override
    public ReviewResponse create(UUID productId, UUID userId, ReviewRequest reviewRequest) throws Exception {
        Product product = productService.findById(productId);
        User user = userService.findById(userId);
        ReviewProduct reviewProduct = new ReviewProduct();
        reviewProduct.setProduct(product);
        reviewProduct.setUser(user);
        if (reviewRequest.getParentReviewId() != null) {
            ReviewProduct parentReview = findById(reviewRequest.getParentReviewId());
            reviewProduct.setParentReviewProduct(parentReview);
        }
        save(reviewProduct, reviewRequest);
        return reviewProductMapper.toReviewResponse(reviewProduct);
    }

    @Override
    public ReviewResponse update(UUID productId, UUID reviewId, UUID userId, ReviewRequest reviewRequest) {
        ReviewProduct reviewProduct = findByIdAndProductId(reviewId, productId);
        checkUserPermission(userId, reviewProduct);
        save(reviewProduct, reviewRequest);
        return reviewProductMapper.toReviewResponse(reviewProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public void countReviewsOfAProduct(ProductResponse productResponse) {
        long reviewCount = reviewProductRepository.countReviewProductByProductId(productResponse.getId());
        productResponse.setReviewCount(reviewCount);
    }

    @Override
    @Transactional(readOnly = true)
    public void countReviewsOfListProduct(List<ProductResponse> productResponses) {
        if (!productResponses.isEmpty()) {
            productResponses.forEach(productResponse -> {
                long reviewCount = reviewProductRepository.countReviewProductByProductId(productResponse.getId());
                productResponse.setReviewCount(reviewCount);
            });
        }
    }

    /**
     * Count replies of review
     *
     * @param reviewResponses
     */
    private void countRepliesOfReview(List<ReviewResponse> reviewResponses) {
        if (!reviewResponses.isEmpty()) {
            reviewResponses.forEach(reviewResponse -> reviewResponse.setReplyCount(reviewProductRepository.countReviewProductByParentReviewProductId(reviewResponse.getId())));
        }
    }

    /**
     * Find review by review id
     *
     * @param reviewId
     * @return ReviewProduct
     */
    private ReviewProduct findById(UUID reviewId) {
        return reviewProductRepository.findById(reviewId).orElseThrow(() -> new NotFoundException(MessageConstant.REVIEW_NOT_FOUND));
    }

    /**
     * Find review by review id & product id
     *
     * @param reviewId
     * @param productId
     * @return ReviewProduct
     */
    private ReviewProduct findByIdAndProductId(UUID reviewId, UUID productId) {
        return reviewProductRepository.findByIdAndProductId(reviewId, productId).orElseThrow(() -> new NotFoundException(MessageConstant.REVIEW_NOT_FOUND));
    }

    /**
     * Check user permission
     *
     * @param currentUserId
     * @param reviewProduct
     */
    private void checkUserPermission(UUID currentUserId, ReviewProduct reviewProduct) {
        if (!currentUserId.equals(reviewProduct.getUser().getId())) {
            throw new BadRequestException(MessageConstant.PERMISSION_DENIED);
        }
    }

    /**
     * Save review entity
     *
     * @param reviewProduct
     * @param reviewRequest
     */
    private void save(ReviewProduct reviewProduct, ReviewRequest reviewRequest) {
        reviewProduct.setReviewContent(reviewRequest.getReviewContent());
        reviewProductRepository.save(reviewProduct);
    }
}
