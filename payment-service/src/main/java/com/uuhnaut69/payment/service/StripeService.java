package com.uuhnaut69.payment.service;

import com.uuhnaut69.core.domain.model.Cart;
import com.uuhnaut69.payment.payload.request.CreditCard;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface StripeService {

  void charge(CreditCard creditCard, Cart cart, UUID userId) throws Exception;
}
