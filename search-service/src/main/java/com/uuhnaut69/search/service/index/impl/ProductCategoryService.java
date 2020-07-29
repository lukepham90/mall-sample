package com.uuhnaut69.search.service.index.impl;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.CategoryEs;
import com.uuhnaut69.search.document.ProductEs;
import com.uuhnaut69.search.service.index.CategoryEsService;
import com.uuhnaut69.search.service.index.ProductEsService;
import com.uuhnaut69.search.service.index.ProductRelationshipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryService implements ProductRelationshipService {

    private final ProductEsService productEsService;

    private final CategoryEsService categoryEsService;

    @Override
    public void handleCdcEvent(
            Map<String, Object> productCategoryAfter,
            Map<String, Object> productCategoryDataBefore,
            Operation operation) {
        log.debug("Handle product <-> category data change event {}", productCategoryAfter);
        String productId = productCategoryAfter.get("product_id").toString();
        String categoryId = productCategoryAfter.get("category_id").toString();
        ProductEs productEs = productEsService.findById(productId);
        CategoryEs categoryEs = categoryEsService.findById(categoryId);
        String categoryIdBefore;

        CategoryEs categoryEsBefore = new CategoryEs();

        if (!productCategoryDataBefore.isEmpty()) {
            categoryIdBefore = productCategoryDataBefore.get("category_id").toString();
            categoryEsBefore = categoryEsService.findById(categoryIdBefore);
        }

        updateCategoryInProduct(productEs, categoryEsBefore.getCategoryName());
        if (!Operation.DELETE.name().equals(operation.name())) {
            productEs.getCategories().add(categoryEs.getCategoryName());
        }
        productEsService.save(productEs);
    }

    private void updateCategoryInProduct(ProductEs productEs, String categoryName) {
        productEs.getCategories().removeIf(s -> s.equals(categoryName));
    }
}
