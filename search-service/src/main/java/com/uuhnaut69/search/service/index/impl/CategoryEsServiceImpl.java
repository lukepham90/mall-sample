package com.uuhnaut69.search.service.index.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.CategoryEs;
import com.uuhnaut69.search.repository.CategoryEsRepository;
import com.uuhnaut69.search.service.index.CategoryEsService;
import com.uuhnaut69.search.service.index.ProductEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@RequiredArgsConstructor
public class CategoryEsServiceImpl implements CategoryEsService {

    private final CategoryEsRepository categoryEsRepository;

    private final ProductEsService productEsService;

    @Override
    public void handleCdcEvent(Map<String, Object> categoryData, Operation operation) {
        final ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        final CategoryEs categoryEs = mapper.convertValue(categoryData, CategoryEs.class);

        Optional<CategoryEs> optional = categoryEsRepository.findById(categoryEs.getId());
        if (Operation.DELETE.name().equals(operation.name())) {
            categoryEsRepository.delete(categoryEs);
        } else {
            optional.ifPresent(es -> productEsService.findByCategoryAndUpdate(es.getCategoryName(), categoryEs));
            categoryEsRepository.save(categoryEs);
        }
    }

    @Override
    public CategoryEs findById(String id) {
        return categoryEsRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageConstant.CATEGORY_NOT_FOUND));
    }
}
