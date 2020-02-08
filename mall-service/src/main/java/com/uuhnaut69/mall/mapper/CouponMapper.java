package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.domain.model.Coupon;
import com.uuhnaut69.mall.payload.request.CouponRequest;
import com.uuhnaut69.mall.payload.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(componentModel = "spring")
public interface CouponMapper {

    /**
     * Mapper code generator from coupon request to coupon entity
     *
     * @param couponRequest
     * @param id
     * @return coupon
     */
    @Mapping(source = "id", target = "id")
    Coupon toCouponEntity(CouponRequest couponRequest, UUID id);

    /**
     * Mapper code generator from coupon entity to coupon response
     *
     * @param coupon
     * @return CouponResponse
     */
    CouponResponse toCouponResponse(Coupon coupon);

    /**
     * Mapper code generator from list coupon entity to list coupon response
     *
     * @param coupons
     * @return List CouponResponse
     */
    List<CouponResponse> toListCouponResponse(List<Coupon> coupons);

}
