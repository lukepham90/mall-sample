package com.uuhnaut69.search.repository;

import com.uuhnaut69.search.document.CategoryEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uuhnaut
 * @project mall
 */
@Repository
public interface CategoryEsRepository extends ElasticsearchRepository<CategoryEs, String> {}
