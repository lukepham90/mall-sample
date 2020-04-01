package com.uuhnaut69.mall.search.service.index;

import com.uuhnaut69.mall.core.utils.Operation;
import com.uuhnaut69.mall.search.document.TagEs;
import com.uuhnaut69.mall.search.document.UserEs;

import java.util.Map;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserEsService {

    /**
     * Updates/Inserts/Delete user data.
     *
     * @param userData  User capture data change
     * @param operation {@link Operation}
     */
    void maintainReadModel(Map<String, Object> userData, Operation operation);

    /**
     * Find by user id
     *
     * @param id User Id
     * @return UserEs
     */
    UserEs findById(String id);

    /**
     * Save user
     *
     * @param userEs {@link UserEs}
     */
    void save(UserEs userEs);

    /**
     * Update tag in list user
     *
     * @param tagNameBefore Tag name before
     * @param tagEs         {@link TagEs}
     */
    void findByTagAndUpdate(String tagNameBefore, TagEs tagEs);
}
