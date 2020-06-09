package com.uuhnaut69.core.payload.request;

import com.uuhnaut69.core.domain.enums.Currency;
import com.uuhnaut69.core.domain.enums.PaymentMethod;
import com.uuhnaut69.core.domain.enums.PaymentStatus;
import lombok.Data;

import java.util.Set;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
public class CartRequest {

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Currency currency;

    private Set<CartItemRequest> orderItems;

    private ShippingRequest shippingRequest;

    private BillingRequest billingRequest;

    private String couponCode;

}
