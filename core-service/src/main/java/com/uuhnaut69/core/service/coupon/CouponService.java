package com.uuhnaut69.core.service.coupon;

import com.uuhnaut69.core.domain.model.Coupon;
import com.uuhnaut69.core.payload.request.CouponRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
public interface CouponService {

    Page<Coupon> findAll(Pageable pageable);

    Coupon findByCode(String code);

    Coupon create(CouponRequest couponRequest);

    Coupon update(UUID id, CouponRequest couponRequest);

    void delete(UUID id);

    void deleteAll(List<UUID> ids);
}
