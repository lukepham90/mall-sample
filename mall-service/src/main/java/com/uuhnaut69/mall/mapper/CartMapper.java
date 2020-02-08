package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.*;
import com.uuhnaut69.mall.payload.request.CartRequest;
import com.uuhnaut69.mall.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface CartMapper {

    /**
     * Mapper code generator from cart request to cart entity
     *
     * @param cartRequest
     * @param id
     * @return Cart
     */
    Cart toCartEntity(CartRequest cartRequest, UUID id);

    /**
     * Mapper code generator from cart entity to cart response
     *
     * @param cart
     * @return CartResponse
     */
    @Mapping(source = "cart.user.email", target = "email")
    CartResponse toCartResponse(Cart cart);

    /**
     * Mapper code generator from list cart entity to list cart response
     *
     * @param carts
     * @return List CartResponse
     */
    List<CartResponse> toListCartResponse(List<Cart> carts);

    /**
     * Mapper code generator from cart entity to cart response
     *
     * @param coupon
     * @return
     */
    CouponResponse toCouponResponse(Coupon coupon);

    /**
     * Mapper code generator from billing entity to billing response
     *
     * @param billing
     * @return BillingResponse
     */
    BillingResponse toBillingResponse(Billing billing);

    /**
     * Mapper code generator from shipping entity to shipping response
     *
     * @param shipping
     * @return ShippingResponse
     */
    ShippingResponse toShippingResponse(Shipping shipping);

    /**
     * Mapper code generator from cart item entity to cart item response
     *
     * @param cartItem
     * @return List CartItemResponse
     */
    @Mapping(source = "cartItem.product.productName", target = "productName")
    @Mapping(source = "cartItem.product.thumbNail", target = "thumbNail")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
