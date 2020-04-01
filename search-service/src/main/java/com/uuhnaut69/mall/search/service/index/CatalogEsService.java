package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.CatalogEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CatalogEsService {

    /**
     * Updates/Inserts/Delete catalog data.
     *
     * @param catalogData Catalog capture data change
     * @param operation   {@link Operation}
     */
    void maintainReadModel(Map<String, Object> catalogData, Operation operation);

    /**
     * Find catalog by id
     *
     * @param id
     * @return CatalogEs
     */
    CatalogEs findById(String id);
}
