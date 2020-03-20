package com.uuhnaut69.mall.search.service.index;

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
     * @param userTagData       User Tag capture data change
     * @param userTagDataBefore User Tag data before
     * @param operation         {@link Operation}
     */
    void maintainReadModel(Map<String, Object> userTagData, Map<String, Object> userTagDataBefore, Operation operation);
}
