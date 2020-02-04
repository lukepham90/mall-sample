package com.uuhnaut69.mall.search.service;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.BrandEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface BrandEsService {

    /**
     * Updates/Inserts/Delete brand data.
     *
     * @param brandData
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> brandData, Operation operation) throws Exception;

    /**
     * Find brand by id
     *
     * @param id
     * @return BrandEs
     * @throws Exception
     */
    BrandEs findById(String id) throws Exception;

}
