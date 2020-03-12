package com.uuhnaut69.mall.search.service.search.impl;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.search.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductEsRepository productEsRepository;

    @Override
    public Page<ProductEs> search(String text, Pageable pageable) {
        return productEsRepository.search(new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery().filter(QueryBuilders.boolQuery()
                                .should(QueryBuilders.multiMatchQuery(text, "description", "originalPrice", "productName")
                                        .fuzziness(Fuzziness.AUTO))
                                .should(QueryBuilders.nestedQuery("catalogEs",
                                        QueryBuilders.termQuery("catalogEs.catalogName", text),
                                        ScoreMode.None))
                                .should(QueryBuilders.nestedQuery("brandEs",
                                        QueryBuilders.termQuery("brandEs.brandName", text),
                                        ScoreMode.None))
                                .should(QueryBuilders.termQuery("tags", text))))
                .withPageable(pageable).build());
    }
}
