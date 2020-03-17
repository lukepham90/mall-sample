package com.uuhnaut69.mall.service.coupon;

import com.uuhnaut69.mall.domain.model.Coupon;
import com.uuhnaut69.mall.payload.request.CouponRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CouponService {
    /**
     * Find all coupons
     *
     * @param pageable
     * @return Page coupon
     */
    Page<Coupon> findAll(Pageable pageable);

    /**
     * Find coupon by code
     *
     * @param code
     * @return Coupon
     */
    Coupon findByCode(String code);

    /**
     * Create coupon
     *
     * @param couponRequest
     * @return Coupon
     */
    Coupon create(CouponRequest couponRequest);

    /**
     * Update coupon
     *
     * @param id
     * @param couponRequest
     * @return Coupon
     */
    Coupon update(UUID id, CouponRequest couponRequest);

    /**
     * Delete coupon
     *
     * @param id
     */
    void delete(UUID id);

    /**
     * Delete coupons
     *
     * @param ids
     */
    void deleteAll(List<UUID> ids);
}
