package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.document.UserEs;
import com.uuhnaut69.mall.search.utils.Operation;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserEsService {

    /**
     * Updates/Inserts/Delete user data.
     *
     * @param userData
     * @param operation
     */
    void maintainReadModel(Map<String, Object> userData, Operation operation);

    /**
     * Find by user id
     *
     * @param id
     * @return
     */
    UserEs findById(String id);

    /**
     * Save user
     *
     * @param userEs
     */
    void save(UserEs userEs);

    /**
     * Update tag in list user
     *
     * @param tagNameBefore
     * @param tagEs
     */
    void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);
}
