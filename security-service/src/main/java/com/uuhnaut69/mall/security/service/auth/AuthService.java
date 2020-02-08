package com.uuhnaut69.mall.security.service.auth;

import com.uuhnaut69.mall.payload.request.SignInRequest;
import com.uuhnaut69.mall.payload.request.SignUpRequest;
import com.uuhnaut69.mall.payload.response.MessageResponse;
import com.uuhnaut69.mall.security.payload.response.JwtResponse;

/**
 * @author uuhnaut
 * @project mall
 */
public interface AuthService {
    /**
     * Sign In
     *
     * @param signInRequest
     * @return JwtResponse
     * @throws Exception
     */
    JwtResponse signIn(SignInRequest signInRequest) throws Exception;

    /**
     * Sign Up
     *
     * @param signUpRequest
     * @return MessageResponse
     * @throws Exception
     */
    MessageResponse signUp(SignUpRequest signUpRequest) throws Exception;
}
