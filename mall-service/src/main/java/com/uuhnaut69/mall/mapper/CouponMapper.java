package com.uuhnaut69.mall.mapper;

import com.uuhnaut69.mall.config.SpringMapstructConfig;
import com.uuhnaut69.mall.domain.model.Coupon;
import com.uuhnaut69.mall.payload.request.CouponRequest;
import com.uuhnaut69.mall.payload.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapstructConfig.class)
public interface CouponMapper {

    /**
     * Mapper code generator from coupon request to coupon entity
     *
     * @param couponRequest {@link CouponRequest}
     * @param coupon        {@link Coupon}
     */
    void toCouponEntity(CouponRequest couponRequest, @MappingTarget Coupon coupon);

    /**
     * Mapper code generator from coupon entity to coupon response
     *
     * @param coupon {@link Coupon}
     * @return CouponResponse
     */
    CouponResponse toCouponResponse(Coupon coupon);

    /**
     * Mapper code generator from list coupon entity to list coupon response
     *
     * @param coupons List {@link Coupon}
     * @return List CouponResponse
     */
    List<CouponResponse> toListCouponResponse(List<Coupon> coupons);

}
