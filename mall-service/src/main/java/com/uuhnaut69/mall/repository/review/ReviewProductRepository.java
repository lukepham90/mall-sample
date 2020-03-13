package com.uuhnaut69.mall.repository.review;

import com.uuhnaut69.mall.domain.model.ReviewProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface ReviewProductRepository extends JpaRepository<ReviewProduct, UUID> {

    Page<ReviewProduct> findAllByProductIdAndParentReviewProductId(Pageable pageable, UUID productId, UUID parentReviewId);

    Optional<ReviewProduct> findByIdAndProductId(UUID reviewId, UUID productId);

    long countReviewProductByParentReviewProductId(UUID reviewId);

    long countReviewProductByProductId(UUID productId);
}
