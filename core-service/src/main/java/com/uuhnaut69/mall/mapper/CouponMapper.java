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

    void toCouponEntity(CouponRequest couponRequest, @MappingTarget Coupon coupon);

    CouponResponse toCouponResponse(Coupon coupon);

    List<CouponResponse> toListCouponResponse(List<Coupon> coupons);

}
