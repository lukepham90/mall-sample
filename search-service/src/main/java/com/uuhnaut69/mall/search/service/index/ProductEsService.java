package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.search.document.ProductEs;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductEsService {

    /**
     * Updates/Inserts/Delete product data.
     *
     * @param productData
     * @param operation
     */
    void maintainReadModel(Map<String, Object> productData, Operation operation);

    /**
     * Update tag in list product
     *
     * @param tagNameBefore
     * @param tagEs
     */
    void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);

    /**
     * Find by product id
     *
     * @param id
     * @return ProductEs
     */
    ProductEs findById(String id);

    /**
     * Save product
     *
     * @param productEs
     */
    void save(ProductEs productEs);
}
