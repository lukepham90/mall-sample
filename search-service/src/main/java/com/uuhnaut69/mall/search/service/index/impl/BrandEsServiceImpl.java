package com.uuhnaut69.mall.search.service.index.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.search.document.BrandEs;
import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.repository.BrandEsRepository;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.index.BrandEsService;
import com.uuhnaut69.mall.search.utils.Operation;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
public class BrandEsServiceImpl implements BrandEsService {

    private final BrandEsRepository brandEsRepository;
    private final ProductEsRepository productEsRepository;

    public BrandEsServiceImpl(BrandEsRepository brandEsRepository, ProductEsRepository productEsRepository) {
        this.brandEsRepository = brandEsRepository;
        this.productEsRepository = productEsRepository;
    }

    @Override
    public void maintainReadModel(Map<String, Object> brandData, Operation operation) {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final BrandEs brandEs = mapper.convertValue(brandData, BrandEs.class);

        Optional<BrandEs> optional = brandEsRepository.findById(brandEs.getId());

        if (Operation.DELETE.name().equals(operation.name())) {
            optional.ifPresent(es -> updateBrandInProducts(es.getId(), null));
            brandEsRepository.deleteById(brandEs.getId());
        } else {
            optional.ifPresent(es -> updateBrandInProducts(es.getId(), brandEs));
            brandEsRepository.save(brandEs);
        }

    }

    @Override
    public BrandEs findById(String id) {
        Optional<BrandEs> optional = brandEsRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundException(MessageConstant.BRAND_NOT_FOUND));
    }

    /**
     * Update brand in products
     *
     * @param brandId Brand Id
     * @param brandEs Brand Document
     */
    private void updateBrandInProducts(String brandId, BrandEs brandEs) {
        List<ProductEs> list = productEsRepository.search(new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.nestedQuery("brandEs", QueryBuilders.matchQuery("brandEs.id", brandId), ScoreMode.None))
                .build()).getContent();
        if (!list.isEmpty()) {
            list.forEach(e -> e.setBrandEs(brandEs));
            productEsRepository.saveAll(list);
        }
    }
}
