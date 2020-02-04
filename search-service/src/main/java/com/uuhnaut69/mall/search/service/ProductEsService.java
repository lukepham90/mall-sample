package com.uuhnaut69.mall.search.service;

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
     * @param productData
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> productData, Operation operation) throws Exception;

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
     * @throws Exception
     */
    ProductEs findById(String id) throws Exception;

    /**
     * Save product
     *
     * @param productEs
     * @throws Exception
     */
    void save(ProductEs productEs) throws Exception;
}
