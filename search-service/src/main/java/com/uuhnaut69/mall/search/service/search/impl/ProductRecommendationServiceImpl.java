package com.uuhnaut69.mall.search.service.search.impl;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.UserEs;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.index.UserEsService;
import com.uuhnaut69.mall.search.service.search.ProductRecommendationService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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
public class ProductRecommendationServiceImpl implements ProductRecommendationService {

    private final UserEsService userEsService;
    private final ProductEsRepository productEsRepository;

    @Override
    public Page<ProductEs> recommendation(String userId, Pageable pageable) {
        UserEs userEs = userEsService.findById(userId);
        String[] excludeProductIds = userEs.getProductIds().toArray(new String[userEs.getProductIds().size()]);
        return productEsRepository
                .search(new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.boolQuery().must(QueryBuilders
                                .functionScoreQuery(QueryBuilders.matchAllQuery(),
                                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                                                /*
                                                  If user's list seen product id doesn't match in list result
                                                  product id => score 8
                                                */
                                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                                        QueryBuilders.boolQuery()
                                                                .mustNot(QueryBuilders.idsQuery()
                                                                        .addIds(excludeProductIds)),
                                                        ScoreFunctionBuilders.weightFactorFunction(8)),
                                                /*
                                                  If user's tags match in product's tags => score 4
                                                */
                                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                                        QueryBuilders.termsQuery("tags", userEs.getTags()),
                                                        ScoreFunctionBuilders.weightFactorFunction(4)),
                                                /*
                                                  If product is marked important => score 2
                                                */
                                                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                                        QueryBuilders.matchQuery("important", true),
                                                        ScoreFunctionBuilders.weightFactorFunction(2))})
                                .boostMode(CombineFunction.REPLACE).scoreMode(FunctionScoreQuery.ScoreMode.SUM)))
                        .withPageable(pageable).withSort(SortBuilders.scoreSort().order(SortOrder.DESC)).build());
    }

}
