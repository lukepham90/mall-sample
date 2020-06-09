package com.uuhnaut69.security.auth.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.core.domain.enums.RoleName;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.payload.request.SignInRequest;
import com.uuhnaut69.core.payload.request.SignUpRequest;
import com.uuhnaut69.core.payload.response.JwtResponse;
import com.uuhnaut69.core.payload.response.MessageResponse;
import com.uuhnaut69.core.repository.user.UserRepository;
import com.uuhnaut69.core.service.mail.MailService;
import com.uuhnaut69.security.auth.AuthService;
import com.uuhnaut69.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final MailService mailService;

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
    public MessageResponse signUp(SignUpRequest signUpRequest) throws Exception {
        checkUserNameValid(signUpRequest.getUsername());
        checkEmailValid(signUpRequest.getEmail());

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setRole(RoleName.ROLE_USER);

        userRepository.save(user);
        mailService.sendMail(user);
        return new MessageResponse(MessageConstant.ACTIVATE_YOUR_ACCOUNT);
    }

    private void checkUserNameValid(String userName) {
        if (userRepository.existsByUsername(userName)) {
            throw new BadRequestException(MessageConstant.USER_NAME_ALREADY_EXIST);
        }
    }

    private void checkEmailValid(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(MessageConstant.USER_EMAIL_ALREADY_EXIST);
        }
    }

}
