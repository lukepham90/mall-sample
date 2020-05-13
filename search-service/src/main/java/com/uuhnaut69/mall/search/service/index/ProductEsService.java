package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.TagEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductEsService {

    void handleCdcEvent(Map<String, Object> productData, Operation operation);

    void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);

    ProductEs findById(String id);

    void save(ProductEs productEs);
}
