package com.uuhnaut69.mall.service.coupon.impl;

import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.exception.BadRequestException;
import com.uuhnaut69.mall.core.exception.NotFoundException;
import com.uuhnaut69.mall.domain.model.Coupon;
import com.uuhnaut69.mall.mapper.CouponMapper;
import com.uuhnaut69.mall.payload.request.CouponRequest;
import com.uuhnaut69.mall.repository.coupon.CouponRepository;
import com.uuhnaut69.mall.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Coupon> findAll(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findByCode(String code) {
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        return coupon.orElseThrow(() -> new NotFoundException(MessageConstant.COUPON_NOT_FOUND));
    }

    @Override
    public Coupon create(CouponRequest couponRequest) {
        checkCouponCodeValid(couponRequest.getCode());
        return save(couponRequest, new Coupon());
    }

    @Override
    public Coupon update(UUID id, CouponRequest couponRequest) {
        Coupon coupon = findById(id);
        if (!coupon.getCode().equals(couponRequest.getCode())) {
            checkCouponCodeValid(couponRequest.getCode());
        }
        return save(couponRequest, coupon);
    }

    @Override
    public void delete(UUID id) {
        Coupon coupon = findById(id);
        couponRepository.delete(coupon);
    }

    @Override
    public void deleteAll(List<UUID> ids) {
        List<Coupon> coupons = couponRepository.findByIdIn(ids);
        couponRepository.deleteAll(coupons);
    }

    /**
     * Find coupon by id
     *
     * @param id
     * @return Coupon
     */
    private Coupon findById(UUID id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        return coupon.orElseThrow(() -> new NotFoundException(MessageConstant.COUPON_NOT_FOUND));
    }

    /**
     * Save coupon entity
     *
     * @param couponRequest
     * @param coupon
     * @return Coupon
     */
    private Coupon save(CouponRequest couponRequest, Coupon coupon) {
        couponMapper.toCouponEntity(couponRequest, coupon);
        return couponRepository.save(coupon);
    }

    /**
     * Check coupon code valid or not
     *
     * @param code
     */
    private void checkCouponCodeValid(String code) {
        if (couponRepository.existsByCode(code)) {
            throw new BadRequestException(MessageConstant.COUPON_ALREADY_EXIST);
        }
    }
}
