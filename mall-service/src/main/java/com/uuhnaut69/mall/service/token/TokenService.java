package com.uuhnaut69.mall.service.token;

import com.uuhnaut69.mall.domain.model.User;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TokenService {
    /**
     * Generate token
     *
     * @param user
     * @return
     * @throws Exception
     */
    String generateToken(User user) throws Exception;

    /**
     * Confirm verify token
     *
     * @param token
     * @throws Exception
     */
    void confirmVerifyToken(String token) throws Exception;
}
