package com.uuhnaut69.mall.search.service;

import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserTagEsService {

    /**
     * Updates/Inserts/Delete user tag data.
     *
     * @param userTagData
     * @param userTagDataBefore
     * @param operation
     * @throws Exception
     */
    void maintainReadModel(Map<String, Object> userTagData, Map<String, Object> userTagDataBefore, Operation operation)
            throws Exception;
}
