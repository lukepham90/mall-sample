package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.*;
import com.uuhnaut69.core.payload.request.CartRequest;
import com.uuhnaut69.core.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface CartMapper {

    void toCartEntity(CartRequest cartRequest, @MappingTarget Cart cart);

    @Mapping(source = "cart.user.email", target = "email")
    CartResponse toCartResponse(Cart cart);

    List<CartResponse> toListCartResponse(List<Cart> carts);

    CouponResponse toCouponResponse(Coupon coupon);

    BillingResponse toBillingResponse(Billing billing);

    ShippingResponse toShippingResponse(Shipping shipping);

    @Mapping(source = "cartItem.product.productName", target = "productName")
    @Mapping(source = "cartItem.product.thumbNail", target = "thumbNail")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
