package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.ReviewProduct;
import com.uuhnaut69.mall.payload.response.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface ReviewProductMapper {

    /**
     * Mapper code generator from review entity to review response
     *
     * @param reviewProduct {@link ReviewProduct}
     * @return ReviewResponse
     */
    @Mapping(source = "reviewProduct.user.id", target = "userId")
    @Mapping(source = "reviewProduct.user.username", target = "username")
    @Mapping(source = "reviewProduct.user.avatar", target = "userAvatar")
    @Mapping(source = "reviewProduct.parentReviewProduct.id", target = "parentReviewId")
    ReviewResponse toReviewResponse(ReviewProduct reviewProduct);

    /**
     * Mapper code generator from list brand entity to list brand response
     *
     * @param reviewProductList List {@link ReviewProduct}
     * @return List {@link ReviewResponse}
     */
    List<ReviewResponse> toListReviewResponse(List<ReviewProduct> reviewProductList);
}
