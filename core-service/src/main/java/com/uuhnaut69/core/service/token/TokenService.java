package com.uuhnaut69.core.service.token;

import com.uuhnaut69.core.domain.model.User;

/**
 * @author uuhnaut
 * @project mall
 */
public interface TokenService {

    String generateToken(User user);

    void confirmVerifyToken(String token);
}
