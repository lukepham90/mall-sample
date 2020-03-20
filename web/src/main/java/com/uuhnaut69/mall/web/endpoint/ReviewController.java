package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.core.utils.PagingUtils;
import com.uuhnaut69.mall.payload.request.ReviewRequest;
import com.uuhnaut69.mall.service.review.ReviewProductService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Review", value = "Product Endpoint")
public class ReviewController {

    private final ReviewProductService reviewProductService;


    /**
     * Get reviews of product (1st hierarchy)
     *
     * @param productId Product Id
     * @param sortBy    Sorted field
     * @param order     Ordered field
     * @param page      Page number
     * @param pageSize  Page size
     * @return GenericResponse
     */
    @ApiOperation(value = "Get reviews of product ENDPOINT", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{productId}" + UrlConstants.REVIEW_URL)
    public GenericResponse getReviewsOfProduct(@PathVariable UUID productId,
                                               @RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                               @RequestParam(value = "order", defaultValue = "desc") String order,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        return GenericResponse.builder().data(reviewProductService.findReviewsOfProduct(pageable, productId)).build();
    }

    /**
     * Get replies of review
     *
     * @param productId Product Id
     * @param reviewId  Review Id
     * @param sortBy    Sorted field
     * @param order     Ordered field
     * @param page      Page number
     * @param pageSize  Page size
     * @return GenericResponse
     */
    @ApiOperation(value = "Get replies of review ENDPOINT", notes = "Public endpoint")
    @GetMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{productId}" + UrlConstants.REVIEW_URL + "/{reviewId}")
    public GenericResponse getRepliesOfReview(@PathVariable UUID productId,
                                              @PathVariable UUID reviewId,
                                              @RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                              @RequestParam(value = "order", defaultValue = "desc") String order,
                                              @RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PagingUtils.makePageRequest(sortBy, order, page, pageSize);
        return GenericResponse.builder().data(reviewProductService.findReplyReviewsOfProduct(pageable, productId, reviewId)).build();
    }

    /**
     * Create a review
     *
     * @param userPrinciple Current User
     * @param productId     Product Id
     * @param reviewRequest {@link ReviewRequest}
     * @return GenericResponse
     */
    @ApiOperation(value = "Create review of product Endpoint", notes = "User endpoint")
    @PostMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{productId}" + UrlConstants.REVIEW_URL)
    public GenericResponse createReview(@CurrentUser UserPrinciple userPrinciple, @PathVariable UUID productId, @RequestBody @Valid ReviewRequest reviewRequest) {
        return GenericResponse.builder().data(reviewProductService.create(productId, userPrinciple.getId(), reviewRequest)).build();
    }

    /**
     * Update a review
     *
     * @param userPrinciple Current User
     * @param productId     Product Id
     * @param reviewId      Review Id
     * @param reviewRequest {@link ReviewRequest}
     * @return GenericResponse
     */
    @ApiOperation(value = "Update review of product Endpoint", notes = "User endpoint")
    @PutMapping(path = UrlConstants.PUBLIC_URL + UrlConstants.PRODUCT_URL + "/{productId}" + UrlConstants.REVIEW_URL + "/{reviewId}")
    public GenericResponse updateReview(@CurrentUser UserPrinciple userPrinciple, @PathVariable UUID productId, @PathVariable UUID reviewId, @RequestBody @Valid ReviewRequest reviewRequest) {
        return GenericResponse.builder().data(reviewProductService.update(productId, reviewId, userPrinciple.getId(), reviewRequest)).build();
    }
}
