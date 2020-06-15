package com.uuhnaut69.core.service.token.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.domain.model.VerifyToken;
import com.uuhnaut69.core.repository.user.UserRepository;
import com.uuhnaut69.core.repository.user.VerifyTokenRepository;
import com.uuhnaut69.core.service.token.TokenService;
import com.uuhnaut69.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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
  public String generateToken(User user) {
    VerifyToken verifyToken = new VerifyToken();
    verifyToken.setUser(user);
    verifyToken.setToken(UUID.randomUUID().toString());
    verifyTokenRepository.save(verifyToken);
    return verifyToken.getToken();
  }

  @Override
  public void confirmVerifyToken(String token) {
    VerifyToken verifyToken = findByToken(token);
    User user = userService.findById(verifyToken.getUser().getId());
    user.setEnabled(true);
    userRepository.save(user);
    verifyTokenRepository.delete(verifyToken);
  }

  private VerifyToken findByToken(String token) {
    Optional<VerifyToken> verifyToken = verifyTokenRepository.findByToken(token);
    return verifyToken.orElseThrow(() -> new BadRequestException(MessageConstant.TOKEN_NOT_FOUND));
  }
}
