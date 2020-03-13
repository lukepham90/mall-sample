package com.uuhnaut69.mall.service.rating.impl;

import com.uuhnaut69.mall.service.rating.RatingAggregationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RatingAggregationRepositoryImpl implements RatingAggregationRepository {

    private final EntityManager entityManager;

    @Override
    public List<Tuple> ratingProductAggregation(UUID productId) {
        StringBuilder ratingProductAggregation = new StringBuilder();
        ratingProductAggregation.append(" SELECT");
        ratingProductAggregation.append(" COUNT(rp.product.id) AS rating_amount,");
        ratingProductAggregation.append(" AVG(rp.rating) AS rating_average");
        ratingProductAggregation.append(" FROM RatingProduct rp WHERE rp.product.id = :productId");
        ratingProductAggregation.append(" GROUP BY rp.product.id");
        return entityManager.createQuery(ratingProductAggregation.toString(), Tuple.class).setParameter("productId", productId).getResultList();
    }
}
