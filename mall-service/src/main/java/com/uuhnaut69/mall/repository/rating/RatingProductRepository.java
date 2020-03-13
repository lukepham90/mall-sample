package com.uuhnaut69.mall.repository.rating;

import com.uuhnaut69.mall.domain.idclass.RatingProductKey;
import com.uuhnaut69.mall.domain.model.RatingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface RatingProductRepository extends JpaRepository<RatingProduct, RatingProductKey>, RatingAggregationRepository {

}
