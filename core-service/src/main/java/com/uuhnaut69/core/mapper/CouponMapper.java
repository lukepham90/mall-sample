package com.uuhnaut69.core.mapper;

import com.uuhnaut69.core.config.SpringMapStructConfig;
import com.uuhnaut69.core.domain.model.Coupon;
import com.uuhnaut69.core.payload.request.CouponRequest;
import com.uuhnaut69.core.payload.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author uuhnaut
 * @project mall
 */
@Mapper(config = SpringMapStructConfig.class)
public interface CouponMapper {

    void toCouponEntity(CouponRequest couponRequest, @MappingTarget Coupon coupon);

    CouponResponse toCouponResponse(Coupon coupon);

    List<CouponResponse> toListCouponResponse(List<Coupon> coupons);
}
