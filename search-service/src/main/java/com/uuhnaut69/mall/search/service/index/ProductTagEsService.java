package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductTagEsService {

    /**
     * Updates/Inserts/Delete product tag data.
     *
     * @param productTagData       Product capture data change
     * @param productTagDataBefore Product data before
     * @param operation            {@link Operation}
     */
    void maintainReadModel(Map<String, Object> productTagData, Map<String, Object> productTagDataBefore,
                           Operation operation);
}
