package com.uuhnaut69.mall.payment.service;

import com.uuhnaut69.mall.domain.model.Cart;
import com.uuhnaut69.mall.payment.payload.request.CreditCard;
import com.uuhnaut69.mall.security.user.UserPrinciple;

/**
 * @author uuhnaut
 * @project mall
 */
public interface StripeService {
    /**
     * Charge via stripe
     *
     * @param creditCard
     * @param cart
     * @param userPrinciple
     * @throws Exception
     */
    void charge(CreditCard creditCard, Cart cart, UserPrinciple userPrinciple) throws Exception;

}
