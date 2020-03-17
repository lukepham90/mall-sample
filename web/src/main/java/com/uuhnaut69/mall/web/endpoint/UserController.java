package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.payload.request.UserBaseContent;
import com.uuhnaut69.mall.service.user.UserService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "User", value = "User Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class UserController {

    private final UserService userService;

    /**
     * Init content what user interests
     *
     * @param userPrinciple
     * @param userBaseContent
     * @return GenericResponse
     */
    @ApiOperation(value = "Init User Content Endpoint", notes = "User endpoint")
    @PostMapping(path = UrlConstants.USER_URL)
    public GenericResponse initBaseContent(@CurrentUser UserPrinciple userPrinciple,
                                           @RequestBody UserBaseContent userBaseContent) {
        log.info("Setup user content");
        userService.initBaseContent(userPrinciple.getId(), userBaseContent);
        return new GenericResponse();
    }

}
