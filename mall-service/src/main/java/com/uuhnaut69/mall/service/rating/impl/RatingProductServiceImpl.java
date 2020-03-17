package com.uuhnaut69.mall.service.rating.impl;

import com.uuhnaut69.mall.domain.idclass.RatingProductKey;
import com.uuhnaut69.mall.domain.model.Product;
import com.uuhnaut69.mall.domain.model.RatingProduct;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payload.response.ProductResponse;
import com.uuhnaut69.mall.repository.rating.RatingProductRepository;
import com.uuhnaut69.mall.service.product.ProductService;
import com.uuhnaut69.mall.service.rating.RatingProductService;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RatingProductServiceImpl implements RatingProductService {

    private static final String RATING_AMOUNT = "rating_amount";
    private static final String RATING_AVERAGE = "rating_average";
    private final ProductService productService;
    private final UserService userService;
    private final RatingProductRepository ratingProductRepository;

    @Override
    public Integer ratingProduct(UUID productId, UUID userId, int rating) throws Exception {
        Product product = productService.findById(productId);
        User user = userService.findById(userId);
        RatingProduct ratingProduct = new RatingProduct();
        RatingProductKey ratingProductKey = new RatingProductKey();
        ratingProductKey.setProductId(productId);
        ratingProductKey.setUserId(userId);
        ratingProduct.setProduct(product);
        ratingProduct.setUser(user);
        ratingProduct.setRating(rating);
        ratingProduct.setId(ratingProductKey);
        return ratingProductRepository.save(ratingProduct).getRating();
    }

    @Override
    public void getRatingAggregationOfProduct(ProductResponse productResponse) {
        extractResultSet(productResponse);
    }

    @Override
    public void getRatingAggregationOfProducts(List<ProductResponse> productResponses) {
        productResponses.forEach(this::extractResultSet);
    }

    /**
     * Extract result set
     *
     * @param productResponse
     * @return ProductResponse
     */
    private void extractResultSet(ProductResponse productResponse) {
        List<Tuple> ratingAggregationResult = ratingProductRepository.ratingProductAggregation(productResponse.getId());
        if (ratingAggregationResult.isEmpty()) {
            productResponse.setRatingAverage((double) 0);
            productResponse.setRatingAmount((long) 0);
        } else {
            productResponse.setRatingAverage((double) ratingAggregationResult.get(0).get(RATING_AVERAGE));
            productResponse.setRatingAmount((long) ratingAggregationResult.get(0).get(RATING_AMOUNT));
        }
    }
}
