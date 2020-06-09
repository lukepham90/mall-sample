package com.uuhnaut69.search.service.index.impl;

import com.uuhnaut69.common.utils.Operation;
import com.uuhnaut69.search.document.ProductEs;
import com.uuhnaut69.search.document.TagEs;
import com.uuhnaut69.search.service.index.ProductEsService;
import com.uuhnaut69.search.service.index.ProductRelationshipService;
import com.uuhnaut69.search.service.index.TagEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@RequiredArgsConstructor
public class ProductTagService implements ProductRelationshipService {

    private final ProductEsService productEsService;

    private final TagEsService tagEsService;

    @Override
    public void handleCdcEvent(Map<String, Object> productTagData, Map<String, Object> productTagDataBefore,
                               Operation operation) {
        String productId = productTagData.get("product_id").toString();
        String tagId = productTagData.get("tag_id").toString();
        ProductEs productEs = productEsService.findById(productId);
        TagEs tagEs = tagEsService.findById(tagId);
        String tagIdBefore;
        TagEs tagEsBefore = new TagEs();
        if (!productTagDataBefore.isEmpty()) {
            tagIdBefore = productTagDataBefore.get("tag_id").toString();
            tagEsBefore = tagEsService.findById(tagIdBefore);
        }

        updateTagInProduct(productEs, tagEsBefore.getTagName());
        if (!Operation.DELETE.name().equals(operation.name())) {
            productEs.getTags().add(tagEs.getTagName());
        }
        productEsService.save(productEs);
    }

    private void updateTagInProduct(ProductEs productEs, String tagName) {
        productEs.getTags().removeIf(tag -> tag.equals(tagName));
    }

}
