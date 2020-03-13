package com.uuhnaut69.mall.repository;

import com.uuhnaut69.mall.domain.idclass.RatingProductKey;
import com.uuhnaut69.mall.domain.model.RatingProduct;
import com.uuhnaut69.mall.service.rating.RatingAggregationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface RatingProductRepository extends JpaRepository<RatingProduct, RatingProductKey>, RatingAggregationRepository {

}
