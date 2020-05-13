package com.uuhnaut69.mall.service.token;

import com.uuhnaut69.mall.domain.model.User;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TokenService {

    String generateToken(User user);

    void confirmVerifyToken(String token);
}
