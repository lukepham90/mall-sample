package com.uuhnaut69.mall.search.repository;

import com.uuhnaut69.mall.search.document.ProductEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface ProductEsRepository extends ElasticsearchRepository<ProductEs, String> {

}
