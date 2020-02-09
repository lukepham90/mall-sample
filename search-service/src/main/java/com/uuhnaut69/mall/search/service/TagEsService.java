package com.uuhnaut69.mall.search.service;

import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TagEsService {

    /**
     * Updates/Inserts/Delete tag data.
     *
     * @param tagData
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> tagData, Operation operation) throws Exception;

    /**
     * Find by id
     *
     * @param id
     * @return TagEs
     * @throws Exception
     */
    TagEs findById(String id) throws Exception;
}
