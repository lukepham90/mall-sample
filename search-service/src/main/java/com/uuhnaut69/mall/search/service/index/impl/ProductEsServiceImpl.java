package com.uuhnaut69.mall.search.service.index.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.repository.ProductEsRepository;
import com.uuhnaut69.mall.search.service.index.ProductEsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEsServiceImpl implements ProductEsService {

    private final ProductEsRepository productEsRepository;

    @Override
    public void maintainReadModel(Map<String, Object> productData, Operation operation) {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final ProductEs productEs = mapper.convertValue(productData, ProductEs.class);
        productEs.setProductSuggest(new Completion(new String[]{productEs.getProductName()}));

        if (Operation.DELETE.name().equals(operation.name())) {
            productEsRepository.deleteById(productEs.getId());
        } else {
            Optional<ProductEs> optional = productEsRepository.findById(productEs.getId());
            optional.ifPresent(es -> productEs.setTags(es.getTags()));
            productEsRepository.save(productEs);
        }
    }

    @Override
    public void findByTagAndUpdate(String tagNameBefore, TagEs tagEs) {
        List<ProductEs> list = productEsRepository.search(
                new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("tags", tagNameBefore)).build())
                .getContent();
        if (!list.isEmpty()) {
            list.forEach(product -> {
                product.getTags().removeIf(tag -> tag.equals(tagNameBefore));
                product.getTags().add(tagEs.getTagName());
            });
            productEsRepository.saveAll(list);
        }
    }

    @Override
    public ProductEs findById(String id) {
        Optional<ProductEs> productEs = productEsRepository.findById(id);
        return productEs.orElseThrow(() -> new NotFoundException(MessageConstant.PRODUCT_NOT_FOUND));
    }

    @Override
    public void save(ProductEs productEs) {
        productEsRepository.save(productEs);
    }

}
