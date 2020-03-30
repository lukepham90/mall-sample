package com.uuhnaut69.security.auth.impl;

import com.uuhnaut69.mall.constant.RabbitMqConstant;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.domain.enums.RoleName;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.payload.request.SignInRequest;
import com.uuhnaut69.mall.payload.request.SignUpRequest;
import com.uuhnaut69.mall.payload.response.JwtResponse;
import com.uuhnaut69.mall.payload.response.MessageResponse;
import com.uuhnaut69.mall.repository.user.UserRepository;
import com.uuhnaut69.security.auth.AuthService;
import com.uuhnaut69.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RabbitTemplate rabbitTemplate;

    private final PasswordEncoder encoder;

    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    @Override
    public MessageResponse signUp(SignUpRequest signUpRequest) {
        checkUserNameValid(signUpRequest.getUsername());
        checkEmailValid(signUpRequest.getEmail());

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setRole(RoleName.ROLE_USER);

        userRepository.save(user);
        rabbitTemplate.convertAndSend(RabbitMqConstant.SEND_ACTIVATE_MAIL_TOPIC, user);
        return new MessageResponse(MessageConstant.ACTIVATE_YOUR_ACCOUNT);
    }

    /**
     * Check user name valid or not
     *
     * @param userName User's name
     */
    private void checkUserNameValid(String userName) {
        if (userRepository.existsByUsername(userName)) {
            throw new BadRequestException(MessageConstant.USER_NAME_ALREADY_EXIST);
        }
    }

    /**
     * Check email valid or not
     *
     * @param email User's email
     */
    private void checkEmailValid(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(MessageConstant.USER_EMAIL_ALREADY_EXIST);
        }
    }

}
