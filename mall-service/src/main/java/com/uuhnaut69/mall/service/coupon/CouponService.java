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
     * @throws Exception
     */
    Coupon findByCode(String code) throws Exception;

    /**
     * Create coupon
     *
     * @param couponRequest
     * @return Coupon
     * @throws Exception
     */
    Coupon create(CouponRequest couponRequest) throws Exception;

    /**
     * Update coupon
     *
     * @param id
     * @param couponRequest
     * @return Coupon
     * @throws Exception
     */
    Coupon update(UUID id, CouponRequest couponRequest) throws Exception;

    /**
     * Delete coupon
     *
     * @param id
     * @throws Exception
     */
    void delete(UUID id) throws Exception;

    /**
     * Delete coupons
     *
     * @param ids
     * @throws Exception
     */
    void deteleAll(List<UUID> ids) throws Exception;
}
