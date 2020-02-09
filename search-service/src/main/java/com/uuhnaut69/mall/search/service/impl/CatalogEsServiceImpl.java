package com.uuhnaut69.mall.search.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.search.document.CatalogEs;
import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.repository.CatalogEsRepository;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.CatalogEsService;
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
public class CatalogEsServiceImpl implements CatalogEsService {

    private final CatalogEsRepository catalogEsRepository;
    private final ProductEsRepository productEsRepository;

    public CatalogEsServiceImpl(CatalogEsRepository catalogEsRepository, ProductEsRepository productEsRepository) {
        this.catalogEsRepository = catalogEsRepository;
        this.productEsRepository = productEsRepository;
    }

    @Override
    public void maintainReadModel(Map<String, Object> catalogData, Operation operation) throws Exception {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final CatalogEs catalogEs = mapper.convertValue(catalogData, CatalogEs.class);

        Optional<CatalogEs> optional = catalogEsRepository.findById(catalogEs.getId());

        if (Operation.DELETE.name().equals(operation.name())) {
            if (optional.isPresent()) {
                updateCatalogInProducts(optional.get().getId(), null);
            }
            catalogEsRepository.deleteById(catalogEs.getId());
        } else {
            if (optional.isPresent()) {
                updateCatalogInProducts(optional.get().getId(), catalogEs);
            }
            catalogEsRepository.save(catalogEs);
        }

    }

    @Override
    public CatalogEs findById(String id) throws Exception {
        Optional<CatalogEs> optional = catalogEsRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundException(MessageConstant.CATALOG_NOT_FOUND));
    }

    /**
     * Update catalog in products
     *
     * @param catalogId
     * @param catalogEs
     * @throws Exception
     */
    private void updateCatalogInProducts(String catalogId, CatalogEs catalogEs) throws Exception {
        List<ProductEs> list = productEsRepository.search(new NativeSearchQueryBuilder().withQuery(QueryBuilders
                .nestedQuery("catalogEs", QueryBuilders.matchQuery("catalogEs.id", catalogId), ScoreMode.None)).build())
                .getContent();
        if (!list.isEmpty()) {
            list.forEach(e -> e.setCatalogEs(catalogEs));

            productEsRepository.saveAll(list);
        }
    }

}
