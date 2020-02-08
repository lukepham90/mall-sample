package com.uuhnaut69.mall.service.token.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.utils.RandomUtils;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.domain.model.VerifyToken;
import com.uuhnaut69.mall.repository.UserRepository;
import com.uuhnaut69.mall.repository.VerifyTokenRepository;
import com.uuhnaut69.mall.service.token.TokenService;
import com.uuhnaut69.mall.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final VerifyTokenRepository verifyTokenRepository;

    @Override
    public String generateToken(User user) throws Exception {
        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setToken(RandomUtils.generateActivationKey());
        verifyToken.setUser(user);
        verifyTokenRepository.save(verifyToken);
        return verifyToken.getToken();
    }

    @Override
    public void confirmVerifyToken(String token) throws Exception {
        VerifyToken verifyToken = findByToken(token);
        User user = userService.findById(verifyToken.getUser().getId());
        user.setEnabled(true);
        userRepository.save(user);
        verifyTokenRepository.delete(verifyToken);
    }

    /**
     * Find verify token by token id
     *
     * @param token
     * @return VerifyToken
     * @throws Exception
     */
    private VerifyToken findByToken(String token) throws Exception {
        Optional<VerifyToken> verifyToken = verifyTokenRepository.findByToken(token);
        return verifyToken.orElseThrow(() -> new BadRequestException(MessageConstant.TOKEN_NOT_FOUND));
    }
}
