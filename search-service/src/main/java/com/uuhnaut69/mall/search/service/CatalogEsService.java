package com.uuhnaut69.mall.search.service;

import com.uuhnaut69.mall.search.document.CatalogEs;
import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CatalogEsService {

    /**
     * Updates/Inserts/Delete catalog data.
     *
     * @param catalogData
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> catalogData, Operation operation) throws Exception;

    /**
     * Find catalog by id
     *
     * @param id
     * @return CatalogEs
     * @throws Exception
     */
    CatalogEs findById(String id) throws Exception;
}
