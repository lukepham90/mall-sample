package com.uuhnaut69.mall.payment.service;

import com.uuhnaut69.mall.domain.model.Cart;
import com.uuhnaut69.mall.payment.payload.request.CreditCard;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface StripeService {
    /**
     * Charge via stripe
     *
     * @param creditCard {@link CreditCard}
     * @param cart       {@link Cart}
     * @param userId     {@link UUID}
     * @throws Exception
     */
    void charge(CreditCard creditCard, Cart cart, UUID userId) throws Exception;

}
