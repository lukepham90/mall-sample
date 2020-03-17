package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.search.document.BrandEs;
import com.uuhnaut69.mall.search.utils.Operation;

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
     */
    void maintainReadModel(Map<String, Object> brandData, Operation operation);

    /**
     * Find brand by id
     *
     * @param id
     * @return BrandEs
     */
    BrandEs findById(String id);

}
