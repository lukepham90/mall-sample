package com.uuhnaut69.mall.search.service;

import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserProductEsService {

    /**
     * Updates/Inserts/Delete user product data.
     *
     * @param userProductData
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> userProductData, Operation operation) throws Exception;
}
