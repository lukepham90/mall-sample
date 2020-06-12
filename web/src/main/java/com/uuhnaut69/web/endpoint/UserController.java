package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.core.payload.request.UserBaseContent;
import com.uuhnaut69.core.service.user.UserService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "User", value = "User Endpoint")
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Init User Content Endpoint", notes = "User endpoint")
    @PostMapping(path = UrlConstants.USER_URL)
    public GenericResponse initBaseContent(@CurrentUser UserPrinciple userPrinciple,
                                           @RequestBody UserBaseContent userBaseContent) {
        userService.initBaseContent(userPrinciple.getId(), userBaseContent);
        return new GenericResponse();
    }

}
