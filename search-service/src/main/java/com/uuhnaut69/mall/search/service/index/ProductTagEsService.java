package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface ProductTagEsService {

    /**
     * Updates/Inserts/Delete product tag data.
     *
     * @param productTagData
     * @param productTagDataBefore
     * @param operation
     */
    void maintainReadModel(Map<String, Object> productTagData, Map<String, Object> productTagDataBefore,
                           Operation operation);
}
