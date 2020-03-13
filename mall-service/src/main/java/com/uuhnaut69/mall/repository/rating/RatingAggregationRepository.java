package com.uuhnaut69.mall.repository.rating;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface RatingAggregationRepository {

    /**
     * Rating product aggregation
     *
     * @param productId
     * @return List {@link Tuple}
     */
    List<Tuple> ratingProductAggregation(UUID productId);

}
