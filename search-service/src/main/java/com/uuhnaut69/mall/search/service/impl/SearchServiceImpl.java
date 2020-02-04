package com.uuhnaut69.mall.search.service.impl;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.SearchService;
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
public class SearchServiceImpl implements SearchService {

    private final ProductEsRepository productEsRepository;

    public SearchServiceImpl(ProductEsRepository productEsRepository) {
        this.productEsRepository = productEsRepository;
    }

    @Override
    public Page<ProductEs> search(String text, Pageable pageable) {
        return productEsRepository.search(new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("isActive", true))
                                .filter(QueryBuilders.boolQuery()
                                        .should(QueryBuilders.multiMatchQuery(text, "description", "originalPrice")
                                                .fuzziness(Fuzziness.AUTO))
                                        .should(QueryBuilders.nestedQuery("catalogEs",
                                                QueryBuilders.matchQuery("catalogEs.catalogName", text)
                                                        .fuzziness(Fuzziness.AUTO),
                                                ScoreMode.None))
                                        .should(QueryBuilders.nestedQuery("brandEs",
                                                QueryBuilders.matchQuery("brandEs.brandName", text)
                                                        .fuzziness(Fuzziness.AUTO),
                                                ScoreMode.None))
                                        .should(QueryBuilders.matchPhrasePrefixQuery("tags", text).slop(2))))
                .withPageable(pageable).build());
    }

}
