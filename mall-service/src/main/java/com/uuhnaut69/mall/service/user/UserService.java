package com.uuhnaut69.mall.service.user;

import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payload.request.UserBaseContent;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserService {
    /**
     * Find user by id
     *
     * @param id {@link UUID}
     * @return User
     */
    User findById(UUID id);

    /**
     * Save
     *
     * @param user {@link User}
     */
    void save(User user);

    /**
     * Init content user wanna see
     *
     * @param id              {@link UUID}
     * @param userBaseContent {@link UserBaseContent}
     */
    void initBaseContent(UUID id, UserBaseContent userBaseContent);

    /**
     * Save uuid product that user has seen
     *
     * @param userId    {@link UUID}
     * @param productId {@link UUID}
     */
    void markAsReadProduct(UUID userId, UUID productId);
}
