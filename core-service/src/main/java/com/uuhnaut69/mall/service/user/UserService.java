package com.uuhnaut69.mall.service.user;

import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payload.request.UserBaseContent;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface UserService {

    User findById(UUID id);

    void save(User user);

    void initBaseContent(UUID id, UserBaseContent userBaseContent);

    void markAsReadProduct(UUID userId, UUID productId);
}
