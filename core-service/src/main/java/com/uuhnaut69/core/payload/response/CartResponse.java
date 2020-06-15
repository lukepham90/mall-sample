package com.uuhnaut69.core.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uuhnaut69.core.domain.enums.Currency;
import com.uuhnaut69.core.domain.enums.PaymentMethod;
import com.uuhnaut69.core.domain.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CartResponse {

  private UUID id;

  private PaymentMethod paymentMethod;

  private PaymentStatus paymentStatus;

  private Currency currency;

  private BigDecimal originalTotalPrice;

  private BigDecimal priceToPay;

  private BigDecimal taxPrice;

  private Set<CartItemResponse> orderItems;

  private ShippingResponse shipping;

  private BillingResponse billing;

  private CouponResponse coupon;

  private String email;
}
