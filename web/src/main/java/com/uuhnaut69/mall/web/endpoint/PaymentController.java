package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.core.constant.UrlConstants;
import com.uuhnaut69.mall.core.payload.response.GenericResponse;
import com.uuhnaut69.mall.payment.payload.request.CreditCard;
import com.uuhnaut69.mall.payment.service.PaymentService;
import com.uuhnaut69.security.user.CurrentUser;
import com.uuhnaut69.security.user.UserPrinciple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API)
@Api(tags = "Payment", value = "Payment Endpoint")
public class PaymentController {

    private final PaymentService paymentService;

    @ApiOperation(value = "Payment Endpoint", notes = "User endpoint")
    @PostMapping(path = UrlConstants.CART_URL + "/{cartId}" + UrlConstants.CHECKOUT)
    public GenericResponse charge(@PathVariable UUID cartId, @RequestBody CreditCard creditCard,
                                  @CurrentUser UserPrinciple userPrinciple) throws Exception {
        paymentService.checkout(cartId, userPrinciple.getId(), creditCard);
        return new GenericResponse();
    }
}
