package com.uuhnaut69.mall.security.service.auth.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.domain.enums.RoleName;
import com.uuhnaut69.mall.domain.model.User;
import com.uuhnaut69.mall.message.constant.RabbitMqConstants;
import com.uuhnaut69.mall.payload.request.SignInRequest;
import com.uuhnaut69.mall.payload.request.SignUpRequest;
import com.uuhnaut69.mall.payload.response.MessageResponse;
import com.uuhnaut69.mall.repository.UserRepository;
import com.uuhnaut69.mall.security.jwt.JwtProvider;
import com.uuhnaut69.mall.security.payload.response.JwtResponse;
import com.uuhnaut69.mall.security.service.auth.AuthService;
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
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtProvider jwtProvider;

    private final RabbitTemplate rabbitTemplate;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           PasswordEncoder encoder, JwtProvider jwtProvider, RabbitTemplate rabbitTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    @Override
    public MessageResponse signUp(SignUpRequest signUpRequest) throws Exception {
        checkUserNameValid(signUpRequest.getUsername());
        checkEmailValid(signUpRequest.getEmail());

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setRole(RoleName.ROLE_USER);

        userRepository.save(user);

        log.info("Publish a message {} to topic {}", user, RabbitMqConstants.SEND_ACTIVATE_MAIL_TOPIC);
        rabbitTemplate.convertAndSend(RabbitMqConstants.SEND_ACTIVATE_MAIL_TOPIC, user);
        return new MessageResponse(MessageConstant.ACTIVATE_YOUR_ACCOUNT);
    }

    /**
     * Check user name valid or not
     *
     * @param userName
     */
    private void checkUserNameValid(String userName) {
        if (userRepository.existsByUsername(userName)) {
            throw new BadRequestException(MessageConstant.USER_NAME_ALREADY_EXIST);
        }
    }

    /**
     * Check email valid or not
     *
     * @param email
     */
    private void checkEmailValid(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(MessageConstant.USER_EMAIL_ALREADY_EXIST);
        }
    }

}
