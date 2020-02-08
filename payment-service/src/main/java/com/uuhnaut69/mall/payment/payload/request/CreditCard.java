package com.uuhnaut69.mall.payment.payload.request;

import lombok.Data;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CreditCard {

    private String cardNumber;

    private String expMonth;

    private String expYear;

    private String cvc;
}
