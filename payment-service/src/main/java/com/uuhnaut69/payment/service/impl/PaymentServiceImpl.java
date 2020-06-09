package com.uuhnaut69.payment.service.impl;

import com.uuhnaut69.common.constant.MessageConstant;
import com.uuhnaut69.common.exception.BadRequestException;
import com.uuhnaut69.core.domain.enums.PaymentMethod;
import com.uuhnaut69.core.domain.enums.PaymentStatus;
import com.uuhnaut69.core.domain.model.Cart;
import com.uuhnaut69.payment.payload.request.CreditCard;
import com.uuhnaut69.payment.service.PaymentService;
import com.uuhnaut69.payment.service.StripeService;
import com.uuhnaut69.core.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CartService cartService;

    private final StripeService stripeService;

    @Override
    public void checkout(UUID cartId, UUID userId, CreditCard creditCard) throws Exception {
        Cart cart = cartService.findByIdAndUserId(cartId, userId);
        if (cart.getPaymentMethod().name().equals(PaymentMethod.STRIPE.name())) {
            if (!cart.getPaymentStatus().name().equals(PaymentStatus.SUCCEED.name())) {
                stripeService.charge(creditCard, cart, userId);
            } else {
                throw new BadRequestException(MessageConstant.PAID_CART);
            }
        } else {
            throw new BadRequestException(MessageConstant.PAYMENT_METHOD_IS_NOT_STRIPE);
        }
    }
}
