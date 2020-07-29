package com.uuhnaut69.payment.service;

import com.uuhnaut69.payment.payload.request.CreditCard;

import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface PaymentService {

    void checkout(UUID cartId, UUID userId, CreditCard creditCard) throws Exception;
}
