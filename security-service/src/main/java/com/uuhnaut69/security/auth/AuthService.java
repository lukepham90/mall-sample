package com.uuhnaut69.security.auth;

import com.uuhnaut69.core.payload.request.SignInRequest;
import com.uuhnaut69.core.payload.request.SignUpRequest;
import com.uuhnaut69.core.payload.response.JwtResponse;
import com.uuhnaut69.core.payload.response.MessageResponse;

/**
 * @author uuhnaut
 * @project mall
 */
public interface AuthService {

    JwtResponse signIn(SignInRequest signInRequest);

    MessageResponse signUp(SignUpRequest signUpRequest) throws Exception;
}
