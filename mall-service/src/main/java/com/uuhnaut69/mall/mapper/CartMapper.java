package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.*;
import com.uuhnaut69.mall.payload.request.CartRequest;
import com.uuhnaut69.mall.payload.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface CartMapper {

    /**
     * Mapper code generator from cart request to cart entity
     *
     * @param cartRequest {@link CartRequest}
     * @param cart        {@link Cart}
     */
    void toCartEntity(CartRequest cartRequest, @MappingTarget Cart cart);

    /**
     * Mapper code generator from cart entity to cart response
     *
     * @param cart {@link Cart}
     * @return CartResponse
     */
    @Mapping(source = "cart.user.email", target = "email")
    CartResponse toCartResponse(Cart cart);

    /**
     * Mapper code generator from list cart entity to list cart response
     *
     * @param carts List {@link Cart}
     * @return List CartResponse
     */
    List<CartResponse> toListCartResponse(List<Cart> carts);

    /**
     * Mapper code generator from cart entity to cart response
     *
     * @param coupon {@link Coupon}
     * @return CouponResponse
     */
    CouponResponse toCouponResponse(Coupon coupon);

    /**
     * Mapper code generator from billing entity to billing response
     *
     * @param billing {@link Billing}
     * @return BillingResponse
     */
    BillingResponse toBillingResponse(Billing billing);

    /**
     * Mapper code generator from shipping entity to shipping response
     *
     * @param shipping {@link Shipping}
     * @return ShippingResponse
     */
    ShippingResponse toShippingResponse(Shipping shipping);

    /**
     * Mapper code generator from cart item entity to cart item response
     *
     * @param cartItem {@link CartItem}
     * @return List CartItemResponse
     */
    @Mapping(source = "cartItem.product.productName", target = "productName")
    @Mapping(source = "cartItem.product.thumbNail", target = "thumbNail")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
