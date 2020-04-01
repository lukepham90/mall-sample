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

    /**
     * Updates/Inserts/Delete product data.
     *
     * @param productData Product capture data change
     * @param operation   {@link Operation}
     */
    void maintainReadModel(Map<String, Object> productData, Operation operation);

    /**
     * Update tag in list product
     *
     * @param tagNameBefore Tag name of product before
     * @param tagEs         {@link TagEs}
     */
    void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);

    /**
     * Find by product id
     *
     * @param id Product Id
     * @return ProductEs
     */
    ProductEs findById(String id);

    /**
     * Save product
     *
     * @param productEs {@link ProductEs}
     */
    void save(ProductEs productEs);
}
