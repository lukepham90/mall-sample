package com.uuhnaut69.mall.search.service.index;

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
     * @param userProductData User Product capture data change
     * @param operation       {@link Operation}
     */
    void maintainReadModel(Map<String, Object> userProductData, Operation operation);
}
