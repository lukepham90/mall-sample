package com.uuhnaut69.web.endpoint;

import com.uuhnaut69.common.constant.UrlConstants;
import com.uuhnaut69.common.payload.response.GenericResponse;
import com.uuhnaut69.payment.payload.request.CreditCard;
import com.uuhnaut69.payment.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.uuhnaut69.security.jwt.SecurityUtils.getCurrentUserId;

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
  public GenericResponse charge(@PathVariable UUID cartId, @RequestBody CreditCard creditCard)
      throws Exception {
    paymentService.checkout(cartId, getCurrentUserId().get(), creditCard);
    return new GenericResponse();
  }
}
