package com.uuhnaut69.mall.search.service.index.impl;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.service.index.ProductEsService;
import com.uuhnaut69.mall.search.service.index.ProductTagEsService;
import com.uuhnaut69.mall.search.service.index.TagEsService;
import com.uuhnaut69.mall.search.utils.Operation;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
public class ProductTagEsServiceImpl implements ProductTagEsService {

    private final ProductEsService productEsService;
    private final TagEsService tagEsService;

    public ProductTagEsServiceImpl(ProductEsService productEsService, TagEsService tagEsService) {
        this.productEsService = productEsService;
        this.tagEsService = tagEsService;
    }

    @Override
    public void maintainReadModel(Map<String, Object> productTagData, Map<String, Object> productTagDataBefore,
                                  Operation operation) throws Exception {
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

    /**
     * Update tag in product
     *
     * @param productEs
     * @param tagName
     */
    private void updateTagInProduct(ProductEs productEs, String tagName) {
        productEs.getTags().removeIf(tag -> tag.equals(tagName));
    }

}
