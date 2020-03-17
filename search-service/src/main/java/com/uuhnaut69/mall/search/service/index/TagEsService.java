package com.uuhnaut69.mall.search.service.index;

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
     */
    void maintainReadModel(Map<String, Object> tagData, Operation operation);

    /**
     * Find by id
     *
     * @param id
     * @return TagEs
     */
    TagEs findById(String id);
}
