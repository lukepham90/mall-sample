package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.payload.request.SignInRequest;
import com.uuhnaut69.mall.payload.request.SignUpRequest;
import com.uuhnaut69.mall.payload.response.JwtResponse;
import com.uuhnaut69.mall.payload.response.MessageResponse;
import com.uuhnaut69.mall.service.auth.AuthService;
import com.uuhnaut69.mall.service.token.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Authentication", value = "Authentication Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API + UrlConstants.PUBLIC_URL)
public class AuthController {

    private final AuthService authService;

    private final TokenService tokenService;

    /**
     * Sign in
     *
     * @param signInRequest
     * @return GenericResponse // status code 200
     * @throws Exception
     */
    @ApiOperation(value = "SignIn Endpoint")
    @PostMapping(path = UrlConstants.AUTH_URL + "/signin")
    public GenericResponse signIn(@RequestBody @Valid SignInRequest signInRequest) throws Exception {
        JwtResponse jwtResponse = authService.signIn(signInRequest);
        log.info("Generate token {}", jwtResponse);
        return GenericResponse.builder().data(jwtResponse).build();
    }

    /**
     * Sign Up
     *
     * @param signUpRequest
     * @return GenericResponse // status code 200
     * @throws Exception
     */
    @ApiOperation(value = "SignUp Endpoint")
    @PostMapping(path = UrlConstants.AUTH_URL + "/signup")
    public GenericResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws Exception {
        log.info("Sign up user with {}", signUpRequest);
        MessageResponse messageResponse = authService.signUp(signUpRequest);
        return GenericResponse.builder().data(messageResponse).build();
    }

    /**
     * Confirm to activate account
     *
     * @param token
     * @return status code 200
     * @throws Exception
     */
    @ApiOperation(value = "Confirmation Endpoint")
    @GetMapping(path = UrlConstants.AUTH_URL + "/confirm")
    public GenericResponse confirmation(@RequestParam(value = "token", defaultValue = "") String token)
            throws Exception {
        tokenService.confirmVerifyToken(token);
        log.info("Generate a verify token {}", token);
        return GenericResponse.builder().build();
    }

}